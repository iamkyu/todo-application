package todoapp.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.web.model.FeatureTogglesProperties;

@RestController
@RequestMapping(value = "/api/feature-toggles", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FeatureToggleController {
    private final FeatureTogglesProperties featureTogglesProperties;

    public FeatureToggleController(FeatureTogglesProperties featureTogglesProperties) {
        this.featureTogglesProperties = featureTogglesProperties;
    }

    @GetMapping
    public FeatureTogglesProperties get() {
        return featureTogglesProperties;
    }
}
