package com.multivaluedictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Program {

	public static void main(String[] args) throws IOException{
		System.out.println("Running program");

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		MultiValueDictionary multiValueDictionary = new MultiValueDictionary();

		while (true) {
			System.out.print("> ");
			String line = reader.readLine();
			if (line.equalsIgnoreCase("exit")) break;
			System.out.println(multiValueDictionary.performAction(line));
		}
	}

}
