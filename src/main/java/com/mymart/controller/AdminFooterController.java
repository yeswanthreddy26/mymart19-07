package com.mymart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mymart.model.Aboutus;
import com.mymart.model.Consumerpolicy;
import com.mymart.model.Customersupport;
import com.mymart.model.Downloadapps;
import com.mymart.model.Keepintouch;
import com.mymart.model.Mailus;
import com.mymart.model.Roaddress;
import com.mymart.model.AboutusDto;
import com.mymart.repository.AboutusRepository;
import com.mymart.repository.ConsumerpolicyRepository;
import com.mymart.repository.CustomersupportRepository;
import com.mymart.repository.DownloadappsRepository;
import com.mymart.repository.KeepintouchRepository;
import com.mymart.repository.MailusRepository;
import com.mymart.repository.RoaddressRepository;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/Admin")
public class AdminFooterController
{
    @Autowired
    private AboutusRepository repo;

    @Autowired
    private ConsumerpolicyRepository crepo;
    
    @Autowired
    private CustomersupportRepository srepo;
    
    @Autowired
    private DownloadappsRepository drepo;
    
    
    @Autowired
    private KeepintouchRepository krepo;
    
    @Autowired
    private MailusRepository mrepo;
    
    @Autowired
    private RoaddressRepository rrepo;



    @GetMapping("/Adminfooter")

    public String showaboutusList(Model model)

    {

        List<Aboutus> aboutus = repo.findAll();

        model.addAttribute("aboutus",aboutus);

       
        List<Consumerpolicy> consumerpolicy = crepo.findAll();

        model.addAttribute("consumerpolicy",consumerpolicy);
        
        
        List<Customersupport> customersupport = srepo.findAll();

        model.addAttribute("customersupport",customersupport);

        
        List<Downloadapps> downloadapps = drepo.findAll();

        model.addAttribute("downloadapps",downloadapps);
        
        
        List<Keepintouch> keepintouch = krepo.findAll();

        model.addAttribute("keepintouch",keepintouch);
        
        
        List<Mailus> mailus = mrepo.findAll();

        model.addAttribute("mailus",mailus);
        
        List<Roaddress> roaddress = rrepo.findAll();
        model.addAttribute("roaddress",roaddress);
        
        return "admin/Adminfooter";


    }
    

    @GetMapping("/createfooter")

    public String showCreatefooter(Model model)

    {

        AboutusDto aboutusDto = new AboutusDto();



        model.addAttribute("aboutusDto", aboutusDto);

        return "admin/createfooter";



    }

    @PostMapping("/createfooter")

    public String createfooter(

            @Valid @ModelAttribute AboutusDto aboutusDto,



            BindingResult result            

            )

    {



        if(result.hasErrors())

        {

            return "admin/createfooter";

        }


        Aboutus aboutus = new Aboutus();


        aboutus.setAboutus(aboutusDto.getAboutus());


        repo.save(aboutus);



        return "redirect:/Admin/Adminfooter";



    }

    @GetMapping("/editfooter")
    public String showEditfooterPage(
            Model model,
            @RequestParam long id
            ) {
        try {
            Aboutus aboutus = repo.findById(id).get();
            model.addAttribute("aboutus",aboutus);

            AboutusDto aboutusDto = new AboutusDto();
            aboutusDto.setAboutus(aboutus.getAboutus());

            model.addAttribute("aboutusDto", aboutusDto);

        }
        catch(Exception e) {
            System.out.println("Exception : " +e.getMessage());
            return "redirect:/Admin/Adminfooter";

        }
        return "admin/editfooter";    
    }

    @PostMapping("/editfooter")
    public String updatefooter(
            Model model,
            @RequestParam long id,
            @Valid @ModelAttribute AboutusDto aboutusDto,
            BindingResult result
            ) {
        try {
            Aboutus aboutus = repo.findById(id).get();
            model.addAttribute("aboutus", aboutus);

            if(result.hasErrors()) {
                return "admin/editfooter";
            }


            aboutus.setAboutus(aboutusDto.getAboutus());

            repo.save(aboutus);


        }
        catch(Exception e) {
            System.out.println("Exception: " +e.getMessage());

        }

        return "redirect:/Admin/Adminfooter";

    }

    @GetMapping("/deletefooter")
    public String deletefooter(
            @RequestParam long id
            ) {
        try {
            Aboutus aboutus = repo.findById(id).get();


            //to delete the product

            repo.delete(aboutus);
        }
        catch(Exception e) {
            System.out.println("Exception: " +e.getMessage());
        }
        return "redirect:/Admin/Adminfooter";
    }
}