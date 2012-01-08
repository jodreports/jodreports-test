/**
 * This file is part of "JODReports Testing Support Library".
 *
 * Copyright (C) 2011-2012 Ansgar Konermann and contributing authors.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the  GNU Lesser General Public License  as published
 * by the  Free Software Foundation,  either version 3  of the License, or
 * (at your option) any later version.
 *
 * This program is  distributed in  the hope that  it will be  useful, but
 * WITHOUT   ANY   WARRANTY;   without   even   the  implied  warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see http://www.gnu.org/licenses/
 */
package org.jodreports.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.fest.assertions.Assertions.assertThat;

public class DocumentElementFilter<ITEM_TYPE> {

  private Iterable<ITEM_TYPE> unfilteredItems;

  DocumentElementFilter(Iterable<ITEM_TYPE> unfilteredItems) {
    this.unfilteredItems = unfilteredItems;
  }

  public static <T> DocumentElementFilter<T> filter(Iterable<T> unfilteredItems) {
    return new DocumentElementFilter<T>(unfilteredItems);
  }

  public static <T> DocumentElementFilter<T> filter(Iterator<T> unfilteredItemIterator) {
    return new DocumentElementFilter<T>(new UnmodifiableIteratorToIterable<T>(unfilteredItemIterator));
  }

  public Collection<ITEM_TYPE> keepItems(Predicate<ITEM_TYPE> keepPredicate) {
    final Collection<ITEM_TYPE> resultCollection = new ArrayList<ITEM_TYPE>();
    for (ITEM_TYPE item : unfilteredItems) {
      if (keepPredicate.isSatisfiedFor(item)) {
        resultCollection.add(item);
      }
    }
    return resultCollection;
  }

  public Collection<ITEM_TYPE> removeItems(Predicate<ITEM_TYPE> removePredicate) {
    final Collection<ITEM_TYPE> resultCollection = new ArrayList<ITEM_TYPE>();
    for (ITEM_TYPE item : unfilteredItems) {
      if (!removePredicate.isSatisfiedFor(item)) {
        resultCollection.add(item);
      }
    }
    return resultCollection;
  }
}

class UnmodifiableIteratorToIterable<ElementType> implements Iterable<ElementType> {

  private Iterator<ElementType> adaptee;

  public UnmodifiableIteratorToIterable(Iterator<ElementType> adaptee) {
    assertThat((Object) adaptee).as("Adaptee darf keine Nullreferenz sein.").isNotNull();
    this.adaptee = adaptee;
  }

  public static <E> Iterable<E> createForCollection(Collection<E> collection) {
    return new UnmodifiableIteratorToIterable<E>(collection.iterator());
  }

  public Iterator<ElementType> iterator() {
    return new UnmodifiableIterator<ElementType>(adaptee);
  }
}

class UnmodifiableIterator<ElementType> implements Iterator<ElementType> {

  private Iterator<ElementType> adaptee;

  public UnmodifiableIterator(Iterator<ElementType> adaptee) {
    assertThat(adaptee).as("Adaptee darf keine Nullreferenz sein.").isNotNull();
    this.adaptee = adaptee;
  }

  public boolean hasNext() {
    return adaptee.hasNext();
  }

  public ElementType next() {
    return adaptee.next();
  }

  public void remove() {
    throw new UnsupportedOperationException(this.getClass().getSimpleName() + " unterstützt kein remove()");
  }
}

