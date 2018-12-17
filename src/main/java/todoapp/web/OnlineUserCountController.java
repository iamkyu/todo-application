package todoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class OnlineUserCountController {
    private final UserCounter userCounter = new UserCounter();

    @GetMapping(value = "/stream/online-users-counter", produces = "text/event-stream")
    public SseEmitter counter(HttpServletResponse response) {
        return userCounter.subscribe();
    }


    private class UserCounter {
        private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

        public SseEmitter subscribe() {
            SseEmitter emitter = new SseEmitter();
            emitter.onCompletion(() -> {
                emitters.remove(emitter);
                broadcast();
            });
            emitter.onTimeout(() -> {
                emitters.remove(emitter);
                broadcast();
            });

            emitters.add(emitter);
            broadcast();

            return emitter;
        }

        private void broadcast() {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event().data(emitters.size()));
                } catch (IOException e) {
                    emitters.remove(emitter);
                }
            }
        }
    }
}
