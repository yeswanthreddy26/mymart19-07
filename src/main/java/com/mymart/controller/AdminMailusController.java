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



import com.mymart.model.MailusDto;

import com.mymart.model.Mailus;

import com.mymart.repository.MailusRepository;



import jakarta.validation.Valid;



@Controller



@RequestMapping("/Admin")

public class AdminMailusController 

{



	@Autowired

	private MailusRepository repo;

	









	@GetMapping("/createmailus")



	public String showCreatemailus(Model model)



	{



		MailusDto mailusDto = new MailusDto();







		model.addAttribute("mailusDto", mailusDto);



		return "admin/createmailus";







	}



	@PostMapping("/createmailus")



	public String createfooter(



			@Valid @ModelAttribute MailusDto mailusDto,







			BindingResult result			



			)



	{







		if(result.hasErrors())



		{



			return "admin/createmailus";



		}





		Mailus mailus = new Mailus();





		mailus.setMailus(mailusDto.getMailus());





		repo.save(mailus);







		return "redirect:/Admin/Adminfooter";







	}



	@GetMapping("/editmailus")

	public String showEditmailusPage(

			Model model,

			@RequestParam long id

			) {

		try {

			Mailus mailus = repo.findById(id).get();

			model.addAttribute("mailus",mailus);



			MailusDto mailusDto = new MailusDto();

			mailusDto.setMailus(mailus.getMailus());



			model.addAttribute("mailusDto", mailusDto);



		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Adminfooter";



		}

		return "admin/editmailus";	

	}



	@PostMapping("/editmailus")

	public String updatemailus(

			Model model,

			@RequestParam long id,

			@Valid @ModelAttribute MailusDto mailusDto,

			BindingResult result

			) {

		try {

			Mailus mailus = repo.findById(id).get();

			model.addAttribute("mailus", mailus);



			if(result.hasErrors()) {

				return "admin/editmailus";

			}





			mailus.setMailus(mailusDto.getMailus());



			repo.save(mailus);





		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());



		}



		return "redirect:/Admin/Adminfooter";



	}



	@GetMapping("/deletemailus")

	public String deletefooter(

			@RequestParam long id

			) {

		try {

			Mailus mailus = repo.findById(id).get();





			//to delete the product



			repo.delete(mailus);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Adminfooter";

	}

	

}