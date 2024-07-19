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



import com.mymart.model.RoaddressDto;

import com.mymart.model.Roaddress;

import com.mymart.repository.RoaddressRepository;



import jakarta.validation.Valid;



@Controller



@RequestMapping("/Admin")

public class AdminRoaddressController

{



	@Autowired

	private RoaddressRepository repo;

	

	





	@GetMapping("/createroaddress")



	public String showCreateroaddress(Model model)



	{



		RoaddressDto roaddressDto = new RoaddressDto();







		model.addAttribute("roaddressDto", roaddressDto);



		return "admin/createroaddress";







	}



	@PostMapping("/createroaddress")



	public String createroaddress(



			@Valid @ModelAttribute RoaddressDto roaddressDto,







			BindingResult result			



			)



	{







		if(result.hasErrors())



		{



			return "admin/createroaddress";



		}





		Roaddress roaddress = new Roaddress();





		roaddress.setRoaddress(roaddressDto.getRoaddress());





		repo.save(roaddress);







		return "redirect:/Admin/Adminfooter";







	}



	@GetMapping("/editroaddress")

	public String showEditroaddressPage(

			Model model,

			@RequestParam long id

			) {

		try {

			Roaddress roaddress = repo.findById(id).get();

			model.addAttribute("roaddress",roaddress);



			RoaddressDto roaddressDto = new RoaddressDto();

			roaddressDto.setRoaddress(roaddress.getRoaddress());



			model.addAttribute("roaddressDto", roaddressDto);



		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Adminfooter";



		}

		return "admin/editroaddress";	

	}



	@PostMapping("/editroaddress")

	public String updatemailus(

			Model model,

			@RequestParam long id,

			@Valid @ModelAttribute RoaddressDto roaddressDto,

			BindingResult result

			) {

		try {

			Roaddress roaddress = repo.findById(id).get();

			model.addAttribute("roaddress", roaddress);



			if(result.hasErrors()) {

				return "admin/editroaddress";

			}





			roaddress.setRoaddress(roaddressDto.getRoaddress());



			repo.save(roaddress);





		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());



		}



		return "redirect:/Admin/Adminfooter";



	}



	@GetMapping("/deleteroaddress")

	public String deleteroaddress(

			@RequestParam long id

			) {

		try {

			Roaddress roaddress = repo.findById(id).get();





			//to delete the product



			repo.delete(roaddress);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Adminfooter";

	}

	

}