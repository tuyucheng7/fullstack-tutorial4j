package cn.tuyucheng.boot.noconverterfound.controller;

import cn.tuyucheng.boot.noconverterfound.model.Student;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class StudentRestController {

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> get(@PathVariable("id") int id) {
		return ResponseEntity.ok(new Student(id, "John", "Wiliams", "AA"));
	}

	@GetMapping(value = "/student/v2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> getV2(@PathVariable("id") int id) {
		return ResponseEntity.ok(new Student(id, "Kevin", "Cruyff", "AA"));
	}

	@GetMapping(value = "/student/v3/{id}", produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<Student> getV3(@PathVariable("id") int id) {
		return ResponseEntity.ok(new Student(id, "Robert", "Miller", "BB"));
	}
}