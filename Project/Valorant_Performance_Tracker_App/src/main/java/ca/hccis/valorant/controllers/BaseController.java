package ca.hccis.valorant.controllers;

import ca.hccis.valorant.bo.ValorantMatchBO;
import ca.hccis.valorant.jpa.entity.ValorantMatch;
import ca.hccis.valorant.repositories.ValorantMatchRepository;
import ca.hccis.valorant.util.CisUtility;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Base controller which control general functionality in the app.
 *
 * @since 20220624
 * @author BJM
 */
@Controller
public class BaseController {

    private final ValorantMatchRepository _vmr;

    @Autowired
    public BaseController(ValorantMatchRepository vmr) {
        _vmr = vmr;
    }

    /**
     * Send the user to the welcome view. The calculated statistics for each
     * match are added to the model to be charted on the welcome page.
     *
     * @since 20220624
     * @author BJM
     */
    @RequestMapping("/")
    public String home(HttpSession session, Model model) {

        String currentDate = CisUtility.getCurrentDate("yyyy-MM-dd");
        session.setAttribute("currentDate", currentDate);

        ArrayList<String> labels = new ArrayList<>();
        ArrayList<Double> acsValues = new ArrayList<>();
        ArrayList<Double> kdaValues = new ArrayList<>();
        ArrayList<Double> kastValues = new ArrayList<>();

        for (ValorantMatch current : _vmr.findAll()) {
            labels.add(current.getPlayerName() + " - " + current.getAgent() + " (" + current.getMapName() + ")");
            acsValues.add(round2(ValorantMatchBO.calculateAcs(current)));
            kdaValues.add(round2(ValorantMatchBO.calculateKdaRatio(current)));
            kastValues.add(round2(ValorantMatchBO.calculateKastPercentage(current)));
        }

        model.addAttribute("labels", labels);
        model.addAttribute("acsValues", acsValues);
        model.addAttribute("kdaValues", kdaValues);
        model.addAttribute("kastValues", kastValues);

        return "index";
    }

    /**
     * Send the user to the about view.
     *
     * @since 20220624
     * @author BJM
     */
    @RequestMapping("/about")
    public String about() {
        return "other/about";
    }

    private static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
