package org.example;

import java.util.Objects;
import java.util.Set;

/**
 * The Problem class represents a problem with its hash and data.
 */
public class Problem {
  private String hash;
  private Set<String> data;

  /**
   * Constructs a new Problem with the specified hash and data.
   *
   * @param hash the hash of the problem
   * @param data the set of data strings associated with the problem
   */
  public Problem(String hash, Set<String> data) {
    this.hash = hash;
    this.data = data;
  }

  /**
   * Returns the hash of the problem.
   *
   * @return the hash of the problem
   */
  public String getHash() {
    return hash;
  }

  /**
   * Returns the set of data strings associated with the problem.
   *
   * @return the set of data strings associated with the problem
   */
  public Set<String> getData() {
    return data;
  }

  /**
   * Indicates whether some other object is "equal to" this one.
   *
   * @param o the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Problem problem = (Problem) o;
    return Objects.equals(hash, problem.hash) && Objects.equals(data, problem.data);
  }

  /**
   * Returns a hash code value for the object.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(hash, data);
  }
}
