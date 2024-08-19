package org.example.o2o.api.controller.sample;

import org.example.o2o.api.service.sample.SampleService;
import org.example.o2o.domain.member.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/sample")
@RestController
public class SampleController {

	private final SampleService sampleService;

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/member")
	public Member findMemberById(@RequestParam("id") Long id) {
		return sampleService.findMemberById(id);
	}

	@GetMapping("/member2")
	public Member findMemberById2(@RequestParam("id") Long id) {
		return sampleService.findMemberById2(id);
	}

	@PostMapping("/member")
	public Member saveMember(@RequestBody Member member) {
		return sampleService.saveMember(member);
	}

}
