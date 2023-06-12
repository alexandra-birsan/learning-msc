package msc

// https://www.youtube.com/watch?v=vpL-JPHt9qw
// https://blog.rockthejvm.com/givens-vs-implicits/

object Givens {

  case class Person(surname: String, name: String, age: Int)

  val personOrdering: Ordering[Person] = (x: Person, y: Person) =>
    x.age.compareTo(y.age)

  def listPeople(persons: Seq[Person])(
      ordering: Ordering[Person]
  ): Seq[Person] = persons.sorted(ordering)

  def someOtherMethodRequiringOrdering(alice: Person, bob: Person)(
      ordering: Ordering[Person]
  ): Int = ordering.compare(alice, bob)

  // find the "standard" value
  // explicitly call methods

  //   given/ using
  // import givens
  // 1 - import explicitly
  //  import StandardValues.standardPersonOrdering
  // NO LONGER working because I removed the name of the given ordering

  // 2 - import given for a TYPE

  import StandardValues.given Ordering[Person]

  // 3 - import all the givens
  import StandardValues.given

  // derive givens
  // working with Options
  // create a given Ordering[Option[T]] if we had an Ordering[T] in the scope
  //  given optionOrdering[T](using normalOrdering:Ordering[T]): Ordering[Option[T]] = (x:Option[T], y: Option[T]) => {
  //    (x, y) match {
  //      case (None, None) => 0
  //      case (Some(_), None) => 1
  //      case (None, Some(_)) => -1
  //      case (Some(p1), Some(p2)) => normalOrdering.compare(p1, p2)
  //    }
  //  }

  def someMethodRequiringStandardOrdering(persons: List[Person])(using
      Ordering[Person]
  ): List[Person] = persons.sorted

  someMethodRequiringStandardOrdering(
    List(Person("Birsan", "Alex", 27), Person("Birsan", "Teo", 18))
  )

  // why? much cleaner code

  def sortThings[T](things: List[T])(using ordering: Ordering[T]) =
    things.sorted

  val maybePersons: List[Option[Person]] =
    List(None, Some(Person("s", "f", 20)), None)

  // where are the givens useful?
  /*
  - type classes
  - dependency injection
  - contextual abstractions, i.e. ability to use code for some types, but not for others
  - type-level programming
   */

  def main(args: Array[String]): Unit = {
    println(sortThings(maybePersons))
  }
}

object StandardValues {

  import Givens.Person

  given Ordering[Person] = (x: Person, y: Person) => x.age.compareTo(y.age)
}
