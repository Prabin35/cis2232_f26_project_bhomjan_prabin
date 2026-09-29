package ca.hccis.valorant.controllers;

import ca.hccis.valorant.bo.ValorantMatchValidationBO;
import ca.hccis.valorant.entity.ValorantMatchDto;
import ca.hccis.valorant.jpa.entity.ValorantMatch;
import ca.hccis.valorant.repositories.ValorantMatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller to administer crud for Valorant matches. Structure taken from the
 * course sample project (BJM) and adapted for the Valorant Performance Tracker.
 *
 * @author Prabin Bhomjan
 * @since 20260928
 */
@Controller
@RequestMapping("/valorantmatch")
public class ValorantMatchController {

    private final ValorantMatchRepository _vmr;

    @Autowired
    public ValorantMatchController(ValorantMatchRepository vmr) {
        _vmr = vmr;
    }

    private static final Logger logger = LoggerFactory.getLogger(ValorantMatchController.class);

    @RequestMapping("")
    public String home(Model model) {
        Iterable<ValorantMatch> matches = _vmr.findAll();
        model.addAttribute("matches", matches);
        model.addAttribute("match", new ValorantMatch());
        return "valorantmatch/list";
    }

    /**
     * Page to delete an entity
     *
     * @param id ID
     * @return the list page
     */
    @RequestMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        try {
            _vmr.deleteById(id);
            model.addAttribute("messageSuccess", "Match deleted");
        } catch (Exception e) {
            model.addAttribute("messageError", "Exception deleting match");
        }
        Iterable<ValorantMatch> matches = _vmr.findAll();
        model.addAttribute("matches", matches);
        model.addAttribute("match", new ValorantMatch());
        return "valorantmatch/list";
    }

    /**
     * Page to add new entity.
     *
     * @return add
     */
    @RequestMapping("/add")
    public String add(Model model) {
        ValorantMatch valorantMatch = new ValorantMatch();
        model.addAttribute("valorantMatch", valorantMatch);
        return "valorantmatch/add";
    }

    /**
     * Page to edit
     *
     * @param id ID
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Optional<ValorantMatch> valorantMatch = _vmr.findById(id);
        if (valorantMatch.isPresent()) {
            model.addAttribute("valorantMatch", valorantMatch.get());
            return "valorantmatch/add";
        }

        model.addAttribute("messageError", "Could not load the match");
        Iterable<ValorantMatch> matches = _vmr.findAll();
        model.addAttribute("matches", matches);
        model.addAttribute("match", new ValorantMatch());
        return "valorantmatch/list";
    }

    /**
     * Submit method that processes add and edit form submissions
     *
     * @param valorantMatch what is being added or modified
     * @param bindingResult Result of validation
     * @return add with errors or redirect to the list
     */
    @RequestMapping("/submit")
    public String submit(Model model, @Valid @ModelAttribute("valorantMatch") ValorantMatch valorantMatch, BindingResult bindingResult) {
        boolean valid = true;

        //Business validation
        ArrayList<String> validationErrors = ValorantMatchValidationBO.validateKastRounds(valorantMatch);
        if (validationErrors.size() > 0) {
            valid = false;
        }

        if (!valid || bindingResult.hasErrors()) {
            System.out.println("--------------------------------------------");
            System.out.println("Validation error");
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getObjectName() + "-" + error.toString() + "-" + error.getDefaultMessage());
            }
            System.out.println("--------------------------------------------");
            model.addAttribute("valorantMatch", valorantMatch);
            model.addAttribute("businessValidationErrorsKastRounds", validationErrors);
            return "valorantmatch/add";
        }

        _vmr.save(valorantMatch);
        return "redirect:/valorantmatch";
    }

    /**
     * Search for a player name
     *
     * @return view for list
     */
    @RequestMapping("/search")
    public String search(Model model, @ModelAttribute("match") ValorantMatch valorantMatch) {

        //Use the repository method to find any entities which contain the
        //name entered on the list page.
        List<ValorantMatch> matches = _vmr.findByPlayerNameContaining(valorantMatch.getPlayerName());

        model.addAttribute("matches", matches);
        logger.debug("searched for name:" + valorantMatch.getPlayerName());
        return "valorantmatch/list";
    }

    @RequestMapping("/list/edit")
    public String showCreateForm(Model model) {
        ValorantMatchDto valorantMatchForm = new ValorantMatchDto();

        Iterable<ValorantMatch> matches = _vmr.findAll();
        ArrayList<ValorantMatch> tempList = new ArrayList<>();
        for (ValorantMatch current : matches) {
            tempList.add(current);
        }
        valorantMatchForm.setMatches(tempList);
        model.addAttribute("form", valorantMatchForm);
        return "valorantmatch/listedit";
    }

    @RequestMapping("/list/edit/submit")
    public String listEditSubmit(@ModelAttribute ValorantMatchDto form, Model model) {

        _vmr.saveAll(form.getMatches());

        ValorantMatchDto valorantMatchForm = new ValorantMatchDto();

        Iterable<ValorantMatch> matches = _vmr.findAll();
        ArrayList<ValorantMatch> tempList = new ArrayList<>();
        for (ValorantMatch current : matches) {
            tempList.add(current);
        }
        valorantMatchForm.setMatches(tempList);
        model.addAttribute("form", valorantMatchForm);
        return "valorantmatch/listedit";
    }
}
