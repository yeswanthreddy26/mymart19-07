package com.mymart.controller;



import java.io.InputStream;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;



import com.mymart.model.Downloadapps;

import com.mymart.model.DownloadappsDto;

import com.mymart.repository.DownloadappsRepository;



import jakarta.validation.Valid;



@Controller



@RequestMapping("/Admin")

public class AdminDownloadappsController 

{



	@Autowired

	private DownloadappsRepository repo;

	

	@GetMapping("/createdownloadapps")



	public String showCreatedownloadappsPage(Model model)



	{



		DownloadappsDto downloadappsDto = new DownloadappsDto(); 



		model.addAttribute("downloadappsDto", downloadappsDto);



		return "admin/createdownloadapps";



		



	}



	@PostMapping("/createdownloadapps")



	public String createdownloadapps(



			@Valid @ModelAttribute DownloadappsDto downloadappsDto,



			BindingResult result			



			)



	{



		try {



			if(downloadappsDto.getImageFileName().isEmpty())



			{



				result.addError(new FieldError("downloadappsDto","imageFile","The image file is required"));



			}



		} catch (Exception e) {



		



			e.printStackTrace();



		}



		



		if(result.hasErrors())



		{



			return "admin/createdownloadapps";



		}



		



		MultipartFile image = downloadappsDto.getImageFileName();



		String storageFileName = image.getOriginalFilename();



		



		try



	{



			String uploadDir = "public/images/";



			Path uploadPath = Paths.get(uploadDir);



			



			if(!Files.exists(uploadPath))



			{



				Files.createDirectories(uploadPath);



				



			}



			



		try(InputStream inputStream = image.getInputStream())



		{



			Files.copy(inputStream, Paths.get(uploadDir + storageFileName),



					StandardCopyOption.REPLACE_EXISTING);



		}			



	}catch(Exception ex)



		{



		



			System.out.println("Exception : " +ex.getMessage());



			



		}



		



		Downloadapps downloadapps = new Downloadapps();



		



		downloadapps.setImageFileName(storageFileName);



		



		repo.save(downloadapps);



		



		return "redirect:/Admin/Adminfooter";



		



	}

	

	@GetMapping("/editdownloadapps")

	public String showEditdownloadappsPage(

			Model model,

			@RequestParam long id

			) {

		try {

			Downloadapps downloadapps = repo.findById(id).get();

			model.addAttribute("downloadapps",downloadapps);

			

			DownloadappsDto downloadappsDto = new DownloadappsDto();

			

			

			model.addAttribute("downloadappsDto", downloadappsDto);

			

		}

		catch(Exception e) {

			System.out.println("Exception : " +e.getMessage());

			return "redirect:/Admin/Adminfooter";

			

		}

	return "admin/editdownloadapps";	

	}

	

	@PostMapping("/editdownloadapps")

	public String updatedownloadapps(

			Model model,

			@RequestParam long id,

			@Valid @ModelAttribute DownloadappsDto downloadappsDto,

			BindingResult result

			) {

		try {

			Downloadapps downloadapps = repo.findById(id).get();

			model.addAttribute("downloadapps", downloadapps);

			

			if(result.hasErrors()) {

				return "admin/editdownloadapps";

			}

			

			if(!downloadappsDto.getImageFileName().isEmpty()) {

				//for deleting the old images

				String uploadDir = "public/images/";

				Path oldImagePath = Paths.get(uploadDir  + downloadapps.getImageFileName());

				try {

					Files.delete(oldImagePath);

				}

				catch(Exception e) {

					System.out.println("Exception: " +e.getMessage());

				}

				

				//save the new image

				MultipartFile image = downloadappsDto.getImageFileName();

				

				String storageFileName = image.getOriginalFilename();

				

				try(InputStream inputStream = image.getInputStream()){

					Files.copy(inputStream, Paths.get(uploadDir + storageFileName),

							StandardCopyOption.REPLACE_EXISTING);

					

				}

				downloadapps.setImageFileName(storageFileName);

					

				}

			

			repo.save(downloadapps);

			



		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

			

		}

		

		return "redirect:/Admin/Adminfooter";

		

	}

	

	@GetMapping("/deletedownloadapps")

	public String deleteProduct(

			@RequestParam long id

			) {

		try {

			Downloadapps downloadapps = repo.findById(id).get();

			

			//for deleting the product image

			Path imagePath = Paths.get("public/images/" + downloadapps.getImageFileName());

			

			try {

				Files.delete(imagePath);

				

			}

			catch(Exception e) {

				System.out.println("Exception: " +e.getMessage());

				

			}

			

			//to delete the product

			

			repo.delete(downloadapps);

		}

		catch(Exception e) {

			System.out.println("Exception: " +e.getMessage());

		}

		return "redirect:/Admin/Adminfooter";

	}

	

	

	

}
