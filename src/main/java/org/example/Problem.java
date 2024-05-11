package org.example;

import java.util.Set;

/**
 * The Problem record represents a problem with its hash and data.
 */
public record Problem(String hash, Set<String> data) {

  /**
   * Constructs a new Problem with the specified hash and data.
   *
   * @param hash the hash of the problem
   * @param data the set of data strings associated with the problem
   */
  public Problem {
  }

  /**
   * Returns the hash of the problem.
   *
   * @return the hash of the problem
   */
  @Override
  public String hash() {
    return hash;
  }

  /**
   * Returns the set of data strings associated with the problem.
   *
   * @return the set of data strings associated with the problem
   */
  @Override
  public Set<String> data() {
    return data;
  }

}
