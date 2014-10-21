package test;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(method = RequestMethod.POST, value = "/test", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String test(@RequestBody String payload) {

		System.out.println(payload);

		return "Post handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/pframe", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String pframe(@RequestBody ProspeckzFrame frame) {

		System.out.println(frame);

		return "Posting frame handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/ppayload", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String ppayload(@RequestBody ProspeckzPayload payload) {

		System.out.println(payload);

		return "Posting payload handled";

	}

}
