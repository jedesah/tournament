package com.github.jedesah

import com.github.nscala_time.time.Imports._

object Tournament {
  def generate(playAreaAvailabilities: Set[PlayAreaAvailability], rules: Rules, participants: Set[String]): Tournament = ???
}

case class PlayArea(id: String)
case class PlayAreaAvailability(areas: Set[PlayArea], availability: Map[Int, (LocalTime, LocalTime)])

case class Rules(minTimeBeetweenMatch: Duration)

case class Tournament(draw: Match, schedule: Map[String, (PlayArea, LocalTime)])

trait Match {
  val winner: Option[Participant] = None
  def determinedSubMatches: Set[SimpleMatch]
  def leafSubMatches: Set[SimpleMatch]
  def round(nb: Int): Option[Set[Match]]
  def contenders: Set[Participant]
  def update(winner: Participant): Match
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
}

case class Participant(name: String)

class InvalidRequirementsError extends Error