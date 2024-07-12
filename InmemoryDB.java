package com.mypractice;

import java.util.*;
public class Strings {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		HashMap<String,Integer> map=new HashMap<>();
		HashMap<String,Integer> remove=new HashMap<>();
		HashMap<String,Integer> duplicate=new HashMap<>();
		System.out.println("Enter the query: ");
		String query=sc.nextLine();
		Stack<String> queryHistory=new Stack<>();
		int flag=0;
		while(!query.equals("exit")) {
			String s[]=query.split(" "); //set a 30
//		for(String i:s) {
//			System.out.print(i+" ");
//		}
		//System.out.println(s[0]);
			String command=s[0];
		//System.out.println(command+" "+key+" "+val);
		//int val=Integer.parseInt(s[2]);
			if(command.equals("set")) {
				String key=s[1];
				int val=Integer.parseInt(s[2]);
				if(!map.containsKey(key)) {
					map.put(key, val);
				}
				else {
					int dupval=map.get(key);
					duplicate.put(key,dupval);
					map.put(key, val);
				}
			}
			else if(command.equals("update")) {
				String key=s[1];
				int val=Integer.parseInt(s[2]);
				int dupval=map.get(key);
				map.put(key, val);
				duplicate.put(key,dupval);
						
			}
			else if(command.equals("count")) {
				int count=0;
				int value=Integer.parseInt(s[1]);
				for(int i:map.values()){
					if(i==value) {
						count++;
					}
				}
				System.out.println(count);
			}
			else if(command.equals("unset")) {
				String key=s[1];
				int val=map.get(key);
				remove.put(key, val);
				map.remove(key);
				
			}
			else if(command.equals("commit")) {
				System.out.println("Data committed successfully!");
				System.out.print("The hashmap is: ");
				System.out.println(map);
			}
			else if(command.equals("get")) {
				String key=s[1];
				int val=map.get(key);
				System.out.println(val);
			}
			else if(command.equals("begin")) {
				flag=1;
			}
			else if(command.equals("rollback")) {
				while(!queryHistory.peek().equals("begin") && !queryHistory.isEmpty()) {
					String st=queryHistory.peek();
					String str[]=st.split(" ");
					if(st.equals("begin")) {
						break;
					}
					else if(str[0].equals("update")) {
						String key=str[1];
						int prevVal=duplicate.get(key);
						map.put(key,prevVal);
						duplicate.remove(key);
					}
					else if(str[0].equals("set")) {
						String key=str[1];
						int val=Integer.parseInt(str[2]);
						if(duplicate.containsKey(key)) {
							int prevVal=duplicate.get(key);
							map.put(key,prevVal);
							duplicate.remove(key);
						}
						else {
							map.remove(key);
						}
					}
					
					else if(str[0].equals("unset")) {
						String key=str[1];
						int val=remove.get(key);
						map.put(key, val);
					}
					//System.out.println(st);
					queryHistory.pop();
				}
				flag=0;
			}
			else {
				System.out.println("You have entered an invalid query");
			}
			if(flag==1) {
				queryHistory.push(query);
			}
		
		//System.out.println(duplicate);
		//System.out.println(queryHistory);
		//System.out.println(remove);
		System.out.println("print exit to quit");
		query=sc.nextLine();
		}
	}
}
