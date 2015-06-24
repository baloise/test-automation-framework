package com.baloise.testautomation.taf.base.types;

public class TafCheckInstruction extends TafString {

  public static TafCheckInstruction emptyInstruction() {
    return new TafCheckInstruction("");
  }

  public static TafCheckInstruction normalInstruction(String s) {
    if (s == null) {
      return nullInstruction();
    }
    if (s.trim().equalsIgnoreCase(EMPTY)) {
      return emptyInstruction();
    }
    if (s.trim().equalsIgnoreCase(NULL)) {
      return nullInstruction();
    }
    return new TafCheckInstruction(s);
  }

  public static TafCheckInstruction nullInstruction() {
    return new TafCheckInstruction(null);
  }

  public TafCheckInstruction(String s) {
    super(s);
  }
}
