object Part2 {

  def applyTo10(c: C[A]) =
    c.apply(10)

  def test() {
    val c1 = new C((x:Int) => new A(x))
    println(c1.apply(3))

    val c2 = new C((x:Int) => new B(x+1, x+2))
    println(c2.apply(3))

    println(applyTo10(c1))
    println(applyTo10(c2))  //relies on covariant subtyping

    var t1: Tree[A] = new Empty()

    t1 = t1.insert(new A(4))
    t1 = t1.insert(new A(3))
    t1 = t1.insert(new B(4,1))
    t1 = t1.insert(new A(2))
    println(t1)

    def b: B = new B(3, 1)
    println(b.sum())
  }

  def main(args: Array[String]) =
    test()

  def child(a: A) : B = {
    new B(a.x, 1)
  }
  def parent: B => A = child

}

class A(y : Int) extends Ordered[A] {

  def x = y

  override def compare(that: A): Int =
    x.compare(that.x)

  def sum() = {
    x + 1
  }

  override def toString() =
    "A<" + x + ">"
}

class B(a: Int, b: Int) extends A(a + b) {

  def s = a
  def t = b

  override def toString() =
    "B<" + s + "," + t + ">"
}

class C[+T](g: Int=>T) {

  def f = g

  def apply(x: Int) = g(x)
}

abstract class Tree[T <: Ordered[T]]  {
  def insert(x: T): Tree[T]
}

case class Leaf[T <: Ordered[T]](label: T) extends Tree[T] {

  def value = label

  override def insert(x: T): Tree[T] = {
    if (x < value)
      new Node(value, new Leaf(x), new Empty())
    else
      new Node(value,new Empty(), new Leaf(x))
  }

  override def toString() =
    "(" + value + ")"
}

case class Node[T <: Ordered[T]](label: T, lChild: Tree[T], rChild: Tree[T]) extends Tree[T] {

  def value = label
  def right = rChild
  def left = lChild

  override def insert(x: T): Tree[T] = {
    if (x < value)
      new Node(value, left.insert(x), right)
    else
      new Node(value, left, right.insert(x))
  }

  override def toString() =
    "(val:" + value + ", left:" + left + ", right:" + right + ")"
}

case class Empty[T <: Ordered[T]]() extends Tree[T] {

  override def insert(x: T): Tree[T] =
    new Leaf(x)

  override def toString() =
    "Empty"
}
