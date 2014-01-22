package com.github.jedesah

import shapeless.{Sized, Nat}

package object collections {
	implicit class AugmentedSet[T](set: Set[T]) {
		def divide[N <: Nat]: Sized[Set[Set[T]], N] = ???
	}

	object SetExtractor {
		def unapplySeq[T](s: Set[T]) = Some(s.toSeq)
	}

	trait Tree[T] {
		def depth: Int
		def all: Set[Tree[T]]
		def update(from: Tree[T], to: Tree[T])
	}
	trait CompositeNode[T] extends Tree[N] {
		def depth = sub.map(_.depth).max
		def all = this ++ sub.map(_.all).flatten
		def update(from: Tree[T], to: Tree[T]) = if (this == from) to else CompositeNode(sub.map(_.update(from, to)))
	}
	trait LeafNode[T] {
		def depth = 1
		def all = this
		def update(from: Tree[T], to: Tree[T]) = if (this == from) to else this
	}

	trait Tree[T, N] extends Tree[T] {
		def depth: Int
		def all: Set[Tree[T, N]]
		def update(from: Tree[T, N], to: Tree[T, N])
	}
	trait CompositeNode[T, N](sub: Sized[Set[Tree[T, N]], N]) extends Tree[T, N] with CompositeNode[T]
	{
		def update(from: Tree[T, N], to: Tree[T, N]) = if (this == from) to else CompositeNode(sub.map(_.update(from, to)))
	}
	trait LeafNode[T](data: T) extends Tree[T, N] with LeafNode[T] {
		def update(from: Tree[T, N], to: Tree[T, N]) = if (this == from) to else this
	}
}