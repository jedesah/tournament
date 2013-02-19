package com.github.jedesah

import com.github.nscala_time.time.Imports._

object Tournament {
  /** Generate a tournament that satidfies the specified constraints */
  def generate(playAreaAvailabilities: Set[MatchLocationAvailability], rules: Rules, participants: Set[String]): Tournament = ???
}

/** Represents the location of a Match, for a squash tournament, this would be a court.
    For a soccer tournament this would be the playing field. */
case class MatchLocation(id: String)
/** availability: A Map from the day number to the start time and end time */
case class MatchLocationAvailability(areas: Set[MatchLocation], availability: Map[Int, (LocalTime, LocalTime)])

case class Rules(minTimeBeetweenMatch: Duration)

case class Tournament(draw: Match, schedule: Map[Match, (MatchLocation, LocalTime)])

abstract class Match {
  val winner: Option[Participant] = None
  def determinedSubMatches: Set[SimpleMatch]
  def leafSubMatches: Set[SimpleMatch]
  def round(nb: Int): Option[Set[Match]]
  def contenders: Set[Participant]
  def update(winner: Participant): Match
  def findMatchWithParticipant(participant: Participant): Match
}
case class SimpleMatch(first: Participant, second: Participant) extends Match {
  def this(first: Participant, second: Participant, winner_ : Participant) = {
    this(first, second)
    val winner = Some(winner_)
  }
  def determinedSubMatches: Set[SimpleMatch] = ???
  def leafSubMatches: Set[SimpleMatch] = Set()
  def round(nb: Int): Option[Set[Match]] = ???
  def contenders: Set[Participant] = ???
  def update(winner: Participant): Match = ???
  def findMatchWithParticipant(participant: Participant) = ???
}
case class CompositeMatch(first: Match, second: Match) extends Match {
  def this(first: Match, second: Match, winner_ : Participant) = {
    this(first, second)
    val winner = Some(winner_)
  }
  def determinedSubMatches: Set[SimpleMatch] = ???
  def leafSubMatches: Set[SimpleMatch] = ???
  def round(nb: Int): Option[Set[Match]] = ???
  def contenders: Set[Participant] = ???
  def update(match_ : Match, winner: Participant): Match = ???
  def update(winner: Participant): Match = ???
  def findMatchWithParticipant(participant: Participant) = ???
}

case class Participant(name: String)