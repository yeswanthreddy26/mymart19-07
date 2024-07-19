package com.mymart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mymart.model.Consumerpolicy;
import com.mymart.model.ConsumerpolicyDto;
import com.mymart.repository.ConsumerpolicyRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/Admin")
public class AdminConsumerpolicyController {

	 @Autowired
	    private ConsumerpolicyRepository crepo;
	 
	 @GetMapping("/createconsumerpolicy")

	    public String showCreateconsumerpolicy(Model model)

	    {

	        ConsumerpolicyDto consumerpolicyDto = new ConsumerpolicyDto();



	        model.addAttribute("consumerpolicyDto", consumerpolicyDto);

	        return "admin/createconsumerpolicy";



	    }

	    @PostMapping("/createconsumerpolicy")

	    public String createconsumerpolicy(

	            @Valid @ModelAttribute ConsumerpolicyDto consumerpolicyDto,



	            BindingResult result            

	            )

	    {



	        if(result.hasErrors())

	        {

	            return "admin/createconsumerpolicy";

	        }


	        Consumerpolicy consumerpolicy = new Consumerpolicy();


	        consumerpolicy.setConsumerpolicy(consumerpolicyDto.getConsumerpolicy());


	        crepo.save(consumerpolicy);



	        return "redirect:/Admin/Adminfooter";



	    }

	    @GetMapping("/editconsumerpolicy")
	    public String showEditfooterPage(
	            Model model,
	            @RequestParam long id
	            ) {
	        try {
	        	Consumerpolicy consumerpolicy = crepo.findById(id).get();
	            model.addAttribute("consumerpolicy",consumerpolicy);

	            ConsumerpolicyDto consumerpolicyDto = new ConsumerpolicyDto();
	            consumerpolicyDto.setConsumerpolicy(consumerpolicy.getConsumerpolicy());

	            model.addAttribute("consumerpolicyDto", consumerpolicyDto);

	        }
	        catch(Exception e) {
	            System.out.println("Exception : " +e.getMessage());
	            return "redirect:/Admin/Adminfooter";

	        }
	        return "admin/editconsumerpolicy";    
	    }

	    @PostMapping("/editconsumerpolicy")
	    public String updateconsumerpolicy(
	            Model model,
	            @RequestParam long id,
	            @Valid @ModelAttribute ConsumerpolicyDto consumerpolicyDto,
	            BindingResult result
	            ) {
	        try {
	        	Consumerpolicy consumerpolicy = crepo.findById(id).get();
	            model.addAttribute("consumerpolicy", consumerpolicy);

	            if(result.hasErrors()) {
	                return "admin/editconsumerpolicy";
	            }


	            consumerpolicy.setConsumerpolicy(consumerpolicyDto.getConsumerpolicy());

	            crepo.save(consumerpolicy);


	        }
	        catch(Exception e) {
	            System.out.println("Exception: " +e.getMessage());

	        }

	        return "redirect:/Admin/Adminfooter";

	    }

	    @GetMapping("/deleteconsumerpolicy")
	    public String deleteconsumerpolicy(
	            @RequestParam long id
	            ) {
	        try {
	        	Consumerpolicy consumerpolicy = crepo.findById(id).get();


	            //to delete the product

	            crepo.delete(consumerpolicy);
	        }
	        catch(Exception e) {
	            System.out.println("Exception: " +e.getMessage());
	        }
	        return "redirect:/Admin/Adminfooter";
	    }
}
