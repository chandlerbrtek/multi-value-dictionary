package com.multivaluedictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValueDictionary {
  Map<String, List<String>> map = new HashMap<>();

  // Outputs
  private static final String added = ") Added";
  private static final String removed = ") Removed";
  private static final String cleared = ") Cleared";
  private static final String emptySet = ") empty set";
  private static final String falseOutput = ") false";
  private static final String errorNotEnoughArgs = ") ERROR, not enough arguments specified";
  private static final String errorKeyDoesNotExist = ") ERROR, key does not exist";
  private static final String errorMemberDoesNotExist = ") ERROR, member does not exist";
  private static final String errorMemberExistsForKey = ") ERROR, member already exists for key";

  public String performAction(String toSplit) {
    StringBuilder sb = new StringBuilder();
    String[] input = toSplit.split("\\s+");

    switch (input[0].toLowerCase()) {
      case "keys": return keys(sb);
      case "members": return members(sb, input);
      case "add" : return add(input);
      case "remove" : return remove(input);
      case "removeall" : return removeAll(input);
      case "clear" : return clear();
      case "keyexists" : return keyExists(input);
      case "memberexists" : return memberExists(input);
      case "allmembers" : return allMembers(sb);
      case "items" : return items(sb);
      default: return ") ERROR, function does not exist";
    }
  }

  private String keys(StringBuilder sb) {
    if (map.isEmpty()) {
      return emptySet;
    }
    int i = 1;
    for (String key : map.keySet()) {
      if (i > 1) sb.append("\n");
      sb.append(i).append(") ").append(key);
      i++;
    }
    return sb.toString();
  }

  private String members(StringBuilder sb, String[] input) {
    if (input.length < 2) {
      return errorNotEnoughArgs;
    } else if (!map.containsKey(input[1])) {
      return errorKeyDoesNotExist;
    } else {
      int i = 1;
      for (String value : map.get(input[1])) {
        if (i > 1) sb.append("\n");
        sb.append(i).append(") ").append(value);
        i++;
      }
    }
    return sb.toString();
  }

  private String add(String[] input) {
    if (input.length < 3) {
      return errorNotEnoughArgs;
    } else if (map.containsKey(input[1]) && map.get(input[1]).contains(input[2])) {
      return errorMemberExistsForKey;
    } else if (map.containsKey(input[1])) {
      List<String> arrayList = map.get(input[1]);
      arrayList.add(input[2]);
      map.put(input[1], arrayList);
      return added;
    } else {
      ArrayList<String> arrayList = new ArrayList<>();
      arrayList.add(input[2]);
      map.put(input[1], arrayList);
      return added;
    }
  }

  private String remove(String[] input) {
    if (input.length < 3) {
      return errorNotEnoughArgs;
    } else if (!map.containsKey(input[1])) {
      return errorKeyDoesNotExist;
    } else if (!map.get(input[1]).contains(input[2])) {
      return errorMemberDoesNotExist;
    } else {
      List<String> arrayList = map.get(input[1]);
      arrayList.remove(input[2]);
      if (arrayList.isEmpty()) {
        map.remove(input[1]);
      } else {
        map.put(input[1], arrayList);
      }
      return removed;
    }
  }

  private String removeAll(String[] input) {
    if (input.length < 2) {
      return errorNotEnoughArgs;
    } else if (!map.containsKey(input[1])) {
      return errorKeyDoesNotExist;
    } else {
      map.remove(input[1]);
      return removed;
    }

  }

  private String clear() {
    map = new HashMap<>();
    return cleared;
  }

  private String keyExists(String[] input) {
    if (input.length < 2) {
      return errorNotEnoughArgs;
    } else {
      return ") " + map.containsKey(input[1]);
    }
  }

  private String memberExists(String[] input) {
    if (input.length < 3) {
      return errorNotEnoughArgs;
    } else if (!map.containsKey(input[1])) {
      return falseOutput;
    } else {
      return ") " + map.get(input[1]).contains(input[2]);
    }
  }

  private String allMembers(StringBuilder sb) {
    if (map.isEmpty()) {
      return emptySet;
    } else {
      int i = 1;
      for (String key : map.keySet()) {
        for (String value: map.get(key)) {
          if (i > 1) sb.append("\n");
          sb.append(i).append(") ").append(value);
          i++;
        }
      }
    }
    return sb.toString();
  }


  private String items(StringBuilder sb) {
    if (map.isEmpty()) {
      return emptySet;
    } else {
      int i = 1;
      for (String key : map.keySet()) {
        for (String value: map.get(key)) {
          if (i > 1) sb.append("\n");
          sb.append(i).append(") ").append(key).append(": ").append(value);
          i++;
        }
      }
    }
    return sb.toString();
  }

}