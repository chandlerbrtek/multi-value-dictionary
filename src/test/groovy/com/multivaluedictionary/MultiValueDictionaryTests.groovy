package com.multivaluedictionary

import spock.lang.Specification

class MultiValueDictionaryTests extends Specification {

    // Inputs
    def addFooBar = "ADD foo bar"
    def addFooBaz = "ADD foo baz"
    def addBazBang = "ADD baz bang"
    def addBangZip = "ADD bang zip"
    def addBangBar = "ADD bang bar"
    def addBangBaz = "ADD bang baz"
    def keys = "KEYS"

    // Outputs
    def added = ") Added"
    def removed = ") Removed"
    def emptySet = ") empty set"
    def falseOutput = ") false"
    def trueOutput = ") true"
    def errorKeyDoesNotExist = ") ERROR, key does not exist"
    def errorMemberDoesNotExist = ") ERROR, member does not exist"
    def errorNotEnoughArgs = ") ERROR, not enough arguments specified"

    def "test keys"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()

        when:
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addBazBang)
        def outputOne = multiValueDictionary.performAction(keys)

        then:
        outputOne == "1) foo\n2) baz" || outputOne == "1) baz\n2) foo"
    }

    def "test members"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def membersFoo = "MEMBERS foo"
        def membersBad = "MEMBERS bad"
        def membersNotEnoughArgs = "MEMBERS"

        when:
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addFooBaz)
        def outputOne = multiValueDictionary.performAction(membersFoo)
        def outputTwo = multiValueDictionary.performAction(membersBad)
        def outputThree = multiValueDictionary.performAction(membersNotEnoughArgs)

        then:
        outputOne == "1) bar\n2) baz"
        outputTwo == errorKeyDoesNotExist
        outputThree == errorNotEnoughArgs
    }

    def "test add"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def errorMemberExistsForKey = ") ERROR, member already exists for key"
        def addNotEnoughArgs = "ADD"

        when:
        def outputOne = multiValueDictionary.performAction(addFooBar)
        def outputTwo = multiValueDictionary.performAction(addFooBaz)
        def outputThree = multiValueDictionary.performAction(addFooBar)
        def outputFour = multiValueDictionary.performAction(addNotEnoughArgs)

        then:
        outputOne == added
        outputTwo == added
        outputThree == errorMemberExistsForKey
        outputFour == errorNotEnoughArgs
    }

    def "test remove"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def removeFooBar = "REMOVE foo bar"
        def removeFooBaz = "REMOVE foo baz"
        def removeBoomPow = "REMOVE boom pow"
        def rermoveNotEnoughArgs = "REMOVE"

        when:
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addFooBaz)
        def outputOne = multiValueDictionary.performAction(removeFooBar)
        def outputTwo = multiValueDictionary.performAction(removeFooBar)
        def outputThree = multiValueDictionary.performAction(keys)
        def outputFour = multiValueDictionary.performAction(removeFooBaz)
        def outputFive = multiValueDictionary.performAction(keys)
        def outputSix = multiValueDictionary.performAction(removeBoomPow)
        def outputSeven = multiValueDictionary.performAction(rermoveNotEnoughArgs)

        then:
        outputOne == removed
        outputTwo == errorMemberDoesNotExist
        outputThree == "1) foo"
        outputFour == removed
        outputFive == emptySet
        outputSix == errorKeyDoesNotExist
        outputSeven == errorNotEnoughArgs
    }

    def "test removeall"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def removeAllFoo = "REMOVEALL foo"
        def removeAllNotEnoughArgs = "REMOVEALL"

        when:
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addFooBaz)
        def outputOne = multiValueDictionary.performAction(keys)
        def outputTwo = multiValueDictionary.performAction(removeAllFoo)
        def outputThree = multiValueDictionary.performAction(keys)
        def outputFour = multiValueDictionary.performAction(removeAllFoo)
        def outputFive = multiValueDictionary.performAction(removeAllNotEnoughArgs)

        then:
        outputOne == "1) foo"
        outputTwo == removed
        outputThree == emptySet
        outputFour == errorKeyDoesNotExist
        outputFive == errorNotEnoughArgs
    }

    def "test clear"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def clear = "CLEAR"
        def cleared = ") Cleared"

        when:
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addBangZip)
        def outputOne = multiValueDictionary.performAction(keys)
        def outputTwo = multiValueDictionary.performAction(clear)
        def outputThree = multiValueDictionary.performAction(keys)
        def outputFour = multiValueDictionary.performAction(clear)
        def outputFive = multiValueDictionary.performAction(keys)

        then:
        outputOne == "1) foo\n2) bang" || outputOne == "1) bang\n2) foo"
        outputTwo == cleared
        outputThree == emptySet
        outputFour == cleared
        outputFive == emptySet
    }

    def "test keyexists"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def keyExistsFoo = "KEYEXISTS foo"
        def keyExistsNotEnoughArgs = "KEYEXISTS"

        when:
        def outputOne = multiValueDictionary.performAction(keyExistsFoo)
        multiValueDictionary.performAction(addFooBar)
        def outputTwo = multiValueDictionary.performAction(keyExistsFoo)
        def outputThree = multiValueDictionary.performAction(keyExistsNotEnoughArgs)

        then:
        outputOne == falseOutput
        outputTwo == trueOutput
        outputThree == errorNotEnoughArgs
    }

    def "test memberexists"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def memberExistsFooBar = "MEMBEREXISTS foo bar"
        def memberExistsFooBaz = "MEMBEREXISTS foo baz"
        def memberExistsNotEnoughArgs = "MEMBEREXISTS"

        when:
        def outputOne = multiValueDictionary.performAction(memberExistsFooBar)
        multiValueDictionary.performAction(addFooBar)
        def outputTwo = multiValueDictionary.performAction(memberExistsFooBar)
        def outputThree = multiValueDictionary.performAction(memberExistsFooBaz)
        def outputFour = multiValueDictionary.performAction(memberExistsNotEnoughArgs)

        then:
        outputOne == falseOutput
        outputTwo == trueOutput
        outputThree == falseOutput
        outputFour == errorNotEnoughArgs
    }

    def "test allmembers"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def allMembers = "ALLMEMBERS"

        when:
        def outputOne = multiValueDictionary.performAction(allMembers)
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addFooBaz)
        def outputTwo = multiValueDictionary.performAction(allMembers)
        multiValueDictionary.performAction(addBangBar)
        multiValueDictionary.performAction(addBangBaz)
        def outputThree = multiValueDictionary.performAction(allMembers)

        then:
        outputOne == emptySet
        outputTwo == "1) bar\n2) baz"
        outputThree == "1) bar\n2) baz\n3) bar\n4) baz"
    }

    def "test items"() {
        given:
        def multiValueDictionary = new MultiValueDictionary()
        def items = "ITEMS"

        when:
        def outputOne = multiValueDictionary.performAction(items)
        multiValueDictionary.performAction(addFooBar)
        multiValueDictionary.performAction(addFooBaz)
        def outputTwo = multiValueDictionary.performAction(items)
        multiValueDictionary.performAction(addBangBar)
        multiValueDictionary.performAction(addBangBaz)
        def outputThree = multiValueDictionary.performAction(items)

        then:
        outputOne == emptySet
        outputTwo == "1) foo: bar\n2) foo: baz"
        outputThree == "1) foo: bar\n2) foo: baz\n3) bang: bar\n4) bang: baz"
        || outputThree == "1) bang: bar\n2) bang: baz\n3) foo: bar\n4) foo: baz"
    }
}