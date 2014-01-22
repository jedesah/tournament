package com.github.jedesah

import com.github.nscala_time.time.Imports._
import collections._

/** Representation of a simple elimination tournament */
object Tournament {
  


  /** Represents the location of a Match, for a squash tournament, this would be a court.
      For a soccer tournament this would be the playing field. */
  type Location = String
  case class TimeSlot(start: LocalTime, end: LocalTime)
  type Day = Int
  type Availability = Map[Day, TimeSlot]
  type Availabilities = Map[MatchLocation, Availability]
  type Participant = String
  type Id = Int
  type Schedule = Map[ID, (MatchLocation, Day, LocalTime)]

  case class Rules(minTimeBeetweenMatch: Duration, expectedMatch:Duration)

  case class Constraints(rules: Rules, availabilities: Availabilities) {
    /** Determines whether the startTime, location and day is valid given these constraints.
	Carefull, if the MatchLocation is available from 9 am. to 5 pm. and a match is scheduled for 5 pm.
	Then the match's scheduling would be invalid since the MatchLocation is no longer available at the start of the match.
    */
    def isMatchStartTimeValid(start: LocalTime, location: Location, day: Day):Boolean = {
      availabilities(location)(day).start < start /*&&
      availabilities(location)(day).end.-(rules.expectedMatch.toPeriod) > start*/
    }
  }

  trait TournamentNode[N] {
    val goingForward: Option[Participant]
  }
  case class Match[N](participants: Sized[Set[Participant], N], winner: Option[Participant]) extends TournamentNode[N] {
    val goingForward = winner
  }
  case class Bye(participant: Participant) extends TournamentNode[N] {
    val goingForward = Some(participant)
  }

  abstract class Match {
    /** The winner of this Match, None, if the match has not been played yet and has no winner or
	Some(Participant) if the match has been played and won
    */
    val winner: Option[Participant] = None
    /** Returns all Matches to play. ie. the matchs for whom the two participants are already know. */
    def determinedSubMatches: Set[SimpleMatch]
    /** Returns all SimpleMatchs contained directly or indirectly by this Match */
    def leafSubMatches: Set[SimpleMatch]
    /** Returns all matchs that belong to a specified round or None if the round number is invalid. */
    def round(nb: Int): Option[Set[Match]]
    /** Returns all participants who are still contenders for the title. ie they have not lost any of their
    matches as we are modeling a simple elimination tournament.
    */
    def contenders: Set[Participant]
    /** Returns a new Tournament which represents the new state of the tournament resulting form this participants victory */
    def update(winner: Participant): Match
    /** Returns a determined Match involving this participant */
    def findMatchWithParticipant(participant: Participant): Option[Match]
    /** Returns all Matches contained directly or indirectly in this Match */
    def allMatches: Set[Match]
    /** Returns the number of rounds in this Match
	Returns 1 if this match is a ByeMatch or a simple Match;
	Returns the depth of the Tree if this Match is a compisite Match
    */
    def nbRounds: Int
    def uncompleted: Set[Match]
  }
  case class SimpleMatch(left: Participant, right: Participant) extends Match {
    val participants = Set(left, right)
    def this(left: Participant, right: Participant, winner_ : Participant) = {
      this(left, right)
      val winner = Some(winner_)
    }
    def determinedSubMatches: Set[SimpleMatch] = ???
    def leafSubMatches: Set[SimpleMatch] = Set()
    def round(nb: Int): Option[Set[Match]] = if (nb == 1) Some(Set(this)) else None
    def contenders: Set[Participant] = winner.map(Set(_)).getOrElse(participants)
    def update(winner: Participant): Match = if (new SimpleMatch(left, right, winner)
    def findMatchWithParticipant(participant: Participant) = if (participants.contains(participant)) Some(this) else None
    def allMatches: Set[Match] = Set(this)
    def nbRounds: Int = 1
  }
  /** When a tournament does not have a number of participants that is a power of 2, it is necessary to give some
      participants a bye. A bye means a partcipant does not have to participate in the first round of the tournament,
      they simply play against one of the winners of the first round.
  */
  case class ByeMatch(only: Participant) extends Match {
    def determinedSubMatches: Set[SimpleMatch] = ???
    def leafSubMatches: Set[SimpleMatch] = ???
    def round(nb: Int): Option[Set[Match]] = ???
    def contenders: Set[Participant] = ???
    def update(winner: Participant): Match = ???
    def findMatchWithParticipant(participant: Participant): Match = ???
    def allMatches: Set[Match] = ???
    def nbRounds: Int = ???
  }
  /** A CompositeMatch is a Match that opposes the winner of the first match against the winner of the second. */
  case class CompositeMatch(left: Match, right: Match) extends Match {
    def this(left: Match, right: Match, winner_ : Participant) = {
      this(left, right)
      val winner = Some(winner_)
    }
    def determinedSubMatches: Set[SimpleMatch] = ???
    def leafSubMatches: Set[SimpleMatch] = ???
    def round(nb: Int): Option[Set[Match]] = ???
    def contenders: Set[Participant] = ???
    def update(match_ : Match, winner: Participant): Match = ???
    def update(winner: Participant): Match = ???
    def findMatchWithParticipant(participant: Participant): Match = ???
    def allMatches: Set[Match] = ???
    def nbRounds: Int = ???
  }

  trait ID(val id: Id)
  
  /** Generate a tournament that satisfies the specified constraints */
  def generateSchedule(constraints: Constraints, draw: Match, with: Schedule = Map()): Schedule = {
    val usedIds = draw.allMatches.collect { case match_: ID => match_.id } ++ with.keys
    val nonFullDays:Availabilities = constraints.availabilities.mapValues(
      _.filterValues{ case (start, end) => start + constraints.rules.expectedMatch <= end }
    )
    val (earliestLocation, availability) = nonFullDays.minBy(List(
      { case (location, availability) => availability.keys.min },
      { case (location, availability) => availability(availability.keys.min).start}
    ))
    val matchToPlace = draw.uncompleted.toList.sortBy(_.nbRounds).collect{
      case match_: ID if !with.keys.contains(match_.id) => match_
      case match_ => match_
    }.headOption
    matchToPlace.map { match_ =>
      val id = match_ match {
        case match_: ID => match_.id
        case _ => generateID(notIn = usedIds)
      }
      val newAvailability = availability.updated(availability.keys.min, _.copy(start = start + constraints.rules.expectedMatch:Duration))
      val newAvailabilities = constraints.availabilities.updated(earliestLocation, new))
      val newConstraints = constraints.copy(availabilities = newAvailabilities)
      val newDraw = draw.addId(match_, id)
      val newShedule = with + (id -> earliestLocation)
      generateSchedule(newConstraints, newDraw)
    }
  }
  def generateDraw[N](participants: Set[Participant]): Tree[TournamentNode[N]] = {
    participants match {
      case SetExtractor() => throw new IllegalArgumentException("Cannot generate a tournament with no participants")
      case SetExtractor(only) => LeafNode(Bye(only))
      case _ => {
        
        val List(left, right) = participants.toList.shuffle.divide(in = 2)
        CompositeMatch(left, right)
      }
    }
  }
}