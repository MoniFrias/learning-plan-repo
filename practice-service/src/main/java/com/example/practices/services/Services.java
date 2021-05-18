package com.example.practices.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practices.entity.Person;
import com.example.practices.entity.Response;
import com.example.practices.repository.RepositoryPerson;

import net.bytebuddy.dynamic.scaffold.MethodGraph.Linked;


@Service
public class Services {

	@Autowired
	RepositoryPerson repository;
	
	private Response errorResponse(String message) {
		return new Response(false, message, null);
	}

	public Response save(Person person) {
		Response response = new Response();
		response.setData(repository.save(person));
		return response;
	}

	public Response findAll() {
		Response response = new Response();
		List<Person> lisPerson = repository.findAll();
		if(!lisPerson.isEmpty()) {
			List<Person> sortedList = lisPerson.stream().sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());			
			response.setData(sortedList);
			System.out.println(lisPerson.stream().filter(name -> name.getName().equals("Moni")).count());	
			
			return response;
		}
		return errorResponse("TPerson no found"); 
	}

	public Response findById(Long id) {
		Response response = new Response();
		if(id!=null && id>0) {
			Person person= repository.findPersonById(id);
			if(person!=null) {
				response.setData(person);
				return response;
			}
			return errorResponse("Person no found for ID");
		}
		return errorResponse("ID can't be null or zero");
		
	}

	public Response update(Person person, Long id) {
		Response response = new Response();
		if(id!=null & id>0) {
			Person personFound = repository.findPersonById(id);
			if(personFound!=null) {
				personFound.setName(person.getName());
				personFound.setLastName(person.getLastName());
				personFound.setAge(person.getAge());
				response.setData(repository.save(personFound));
				return response;
			}
			return errorResponse("Person no found for ID");
		}
		return errorResponse("Id can't be null or zero");
	}

	public Response deleteById(Long id) {
		Response response = new Response();
		if(id!=null & id>0) {
			Person personFound = repository.findPersonById(id);
			if(personFound!=null) {
				repository.deleteById(id);
				return response;
			}
			return errorResponse("Person no found for ID");
		}
		return errorResponse("Id can't be null or zero");
		
	}
	
	public Response ternaryO() {
		Response response = new Response();
		for(int i=1; i<=100; i++) {
			String result ="";
			result  += (i % 3) ==0 ? "Three":""; 
			result  += (i % 5) ==0 ? "Five":""; 
			System.out.println(!result.isEmpty() ? result : i);			
		}				
		return response;
	} 


	public Response exceptionsIndexOut(int position) {
		Response response = new Response();
		String[] names = {"Moni", "Saul", "John", "Edgar"};
		try {
	        response.setData(names[position]);
	        System.out.println(names[position]);
	        return response;
		}catch (IndexOutOfBoundsException e) {
			System.out.println(e);
			response.setMessage("Exception: "+e);
			return response;
		}finally {
		      System.out.println("The 'try catch' is finished.");
	    }
		
	}

	public Response exceptionsNullPointer(Long id) {
		Response response = new Response();
		Person person = repository.findPersonById(id);
		try {
			String nameUpper = person.getName().toUpperCase();
	        response.setData(nameUpper);
	        System.out.println(nameUpper);
	        return response;
		}catch (NullPointerException e) {
			System.out.println(e);
			response.setMessage("Exception: "+e);
			return response;
		}	
	}

	public Response exceptionsThrow(Long age) throws ArithmeticException {
		Response response = new Response();
		if(age < 18) {
			throw new ArithmeticException("Not valid");
		}
		System.out.println("Valid"); 
		return response;
	}

	public Response list() {
		Response response = new Response();
		List<Person> personList = new ArrayList<>();
		List<Person> personFound = repository.findAll();
		personFound.add(new Person(1L, "Moni", "Frias", 23L));
		if(!personFound.isEmpty()) {
			personList = personFound.stream().filter(p -> p.getName().startsWith("M")).collect(Collectors.toList());
			response.setData(personList);
			
			LinkedList<String> listPerson = new LinkedList<>();
			listPerson.add("Moni");	
			listPerson.add("Saul");
			listPerson.add("Aldo");
			System.out.println("The first person is: " + listPerson.getFirst());
			
			Optional<Person> name = personFound.stream().findFirst();
			if(name.isPresent()) {
				System.out.println("The first person is: " + name);
			}
			
			return response;
		}		
		return errorResponse("Repository empty");
				
	}

	public Response set(String name) {
		Response response = new Response();
		HashSet<String> person = new HashSet<>();
		person.add("Moni");	
		person.add("Aldo");	
		person.add("Moni");	
		response.setData(person);
		System.out.println("set size is "+ person.size());
		
		if(person.contains(name)) {
			person.remove(name);
			return response;
		}
		return errorResponse("There isn't that person");
		
	}

	public Response map() {
		Response response = new Response();
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Moni");
		map.put(2, "Aldo");
		map.put(3, "Saul");
		map.entrySet().stream().forEach(System.out::println);
		map.putIfAbsent(2, "Elizabeth");
		map.putIfAbsent(4, "Chris");
		map.entrySet().stream().forEach(System.out::println);
		
		map.computeIfPresent(2, (k,v) -> k + v);
		System.out.println(map.get(2));
		
		String value1 = map.getOrDefault(7, "New value");
		String value2 = map.getOrDefault(3, "New value");
		System.out.println(value1);
		System.out.println(value2);
		return response;
	}

	public Response lambda() {
		Response response = new Response();
		List<Person> personFound = repository.findAll();
		if(!personFound.isEmpty()) {
			List<String> personList = personFound.stream().map(person ->{
				String name = person.getName().toUpperCase();
				return name;
			}).filter(Objects::nonNull).collect(Collectors.toList());
			response.setData(personList);
			return response;
		}
		return errorResponse("Repository empty");
	}

	public Response parallelstream() {
		Response response = new Response();
		String[] namesArray = {"Moni","Saul","Aldo","Edgar"};
		List<String> names = new ArrayList<>(Arrays.asList(namesArray));
		
		List<String> process = names.parallelStream()
				.map(name -> {
					return name.toUpperCase();
				}).map(name -> {
					return name + " Frias";
				}).collect(Collectors.toList());
		response.setData(process);
		return response;
	}

	

	
}
