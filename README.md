# Tournament

A Scala reprensation of a tournament.

The concept of a Tournament, along with a Draw, a Match, Matchlocation, Availabilities and Days are modeled.

The API supports generation of a tournament given some constraints. Right now, these constraints are the following:

- MatchLocation availabilities
    ex: In a squash tournament, the amount of courts available along with when they are available to be used.
- A minimum rest time for participants between matches

All kinds of information can also be queried from the Match class which can not only be a SimpleMatch which is composed of two participants with an optional winner, but also a CompositeMatch which allows for Match interdependance and ultimetly represents what is commonly reffered to as a draw.

## Current Status

The specifications for this project are expressed through the Scala type system but also with some ScalaTest specifications wich are yet to be implemented.
