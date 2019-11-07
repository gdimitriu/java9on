package charts.student_processor;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class StudentDataProcessor{
	public List<Student> loadStudent() throws IOException{
		Scanner reader = new Scanner(getClass()
			.getClassLoader()
			.getResourceAsStream("students")
		);

		List<Student> students = new ArrayList<>();
		while(reader.hasNext()){
			String line = reader.nextLine();
			String[] elements = line.split(";");
			Student student = new Student(elements);
			students.add(student);
		}

		return students;
	}
}