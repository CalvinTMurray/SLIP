package test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	private ArrayList<ServerPayload> payloads = new ArrayList<ServerPayload>();

	@RequestMapping(method = RequestMethod.POST, value = "/test", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String test(@RequestBody String payload) {

		System.out.println(payload);

		return "Post handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/sframe", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String sframe(@RequestBody ServerFrame frame) {

		System.out.println(frame.toString());

		List<ServerPayload> receivedPayloads = frame.getPayloads();
		int n = receivedPayloads.size();

		for (int i = 0; i < n; i++) {
			payloads.add(receivedPayloads.get(i));
		}

		return "Post for frame handled";

	}

	@RequestMapping(method = RequestMethod.POST, value = "/spayload", headers = { "Content-type=application/json" }, produces = { "application/json" })
	public @ResponseBody String spayload(@RequestBody ServerPayload payload) {

		System.out.println(payload);

		return "Posting payload handled";

	}

	@RequestMapping(method = RequestMethod.GET, value = "/payloads")
	public List<ServerPayload> payloads() {
		return payloads;
	}

}
