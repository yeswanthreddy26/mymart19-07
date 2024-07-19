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

import com.mymart.model.Customersupport;
import com.mymart.model.CustomersupportDto;
import com.mymart.repository.CustomersupportRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/Admin")
public class AdminCustomersupportController {
	
	 @Autowired
	    private CustomersupportRepository srepo;
	 
	 @GetMapping("/createcustomersupport")

	    public String showCreatecustomersupport(Model model)

	    {

		 CustomersupportDto customersupportDto = new CustomersupportDto();



	        model.addAttribute("customersupportDto", customersupportDto);

	        return "admin/createcustomersupport";



	    }

	    @PostMapping("/createcustomersupport")

	    public String createcustomersupport(

	            @Valid @ModelAttribute CustomersupportDto customersupportDto,



	            BindingResult result            

	            )

	    {



	        if(result.hasErrors())

	        {

	            return "admin/createcustomersupport";

	        }


	        Customersupport customersupport = new Customersupport();


	        customersupport.setCustomersupport(customersupportDto.getCustomersupport());


	        srepo.save(customersupport);



	        return "redirect:/Admin/Adminfooter";



	    }

	    @GetMapping("/editcustomersupport")
	    public String showEditcustomersupportPage(
	            Model model,
	            @RequestParam long id
	            ) {
	        try {
	        	Customersupport customersupport = srepo.findById(id).get();
	            model.addAttribute("customersupport",customersupport);

	            CustomersupportDto customersupportDto = new CustomersupportDto();
	            customersupportDto.setCustomersupport(customersupport.getCustomersupport());

	            model.addAttribute("customersupportDto", customersupportDto);

	        }
	        catch(Exception e) {
	            System.out.println("Exception : " +e.getMessage());
	            return "redirect:/Admin/Adminfooter";

	        }
	        return "admin/editcustomersupport";    
	    }

	    @PostMapping("/editcustomersupport")
	    public String updatecustomersupport(
	            Model model,
	            @RequestParam long id,
	            @Valid @ModelAttribute CustomersupportDto customersupportDto,
	            BindingResult result
	            ) {
	        try {
	        	Customersupport customersupport = srepo.findById(id).get();
	            model.addAttribute("customersupport", customersupport);

	            if(result.hasErrors()) {
	                return "admin/editcustomersupport";
	            }


	            customersupport.setCustomersupport(customersupportDto.getCustomersupport());

	            srepo.save(customersupport);


	        }
	        catch(Exception e) {
	            System.out.println("Exception: " +e.getMessage());

	        }

	        return "redirect:/Admin/Adminfooter";

	    }

	    @GetMapping("/deletecustomersupport")
	    public String deletecustomersupport(
	            @RequestParam long id
	            ) {
	        try {
	        	Customersupport customersupport = srepo.findById(id).get();


	            //to delete the product

	            srepo.delete(customersupport);
	        }
	        catch(Exception e) {
	            System.out.println("Exception: " +e.getMessage());
	        }
	        return "redirect:/Admin/Adminfooter";
	    }

}
