package com.spice.hot.web.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spice.hot.domain.Dish;
import com.spice.hot.service.DishService;
import com.spice.hot.web.rest.util.HeaderUtil;
@RestController
@RequestMapping("/api")
public class FileUploadController {
	
	@Autowired
	ServletContext context;
	
	@Autowired
	DishService dishService;
	
	private static String UPLOAD_DIRECTORY="assets/images/";

    private final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    
    @RequestMapping(value="/dishs/{id}/upload", method=RequestMethod.POST)
    public ResponseEntity<Dish>  handleFileUpload(@PathVariable String id,
            @RequestParam("file") MultipartFile file){
        if (!file.isEmpty()) {
            try {
//            	File dest=new File("/src/main/webapp/assets/images/"+file.getOriginalFilename());
            	String uploadPath = context.getRealPath("") + UPLOAD_DIRECTORY;
            	String fileName=uploadPath+file.getOriginalFilename();
            	log.debug("picture "+file.getOriginalFilename());
            	//log.debug(file.getSize());
                byte[] bytes = file.getBytes();
//                String name=new File(file.getOriginalFilename());
                
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(fileName)));
                
                stream.write(bytes);
                stream.close();
                Dish dish=dishService.findOne(id);
            	dish.setImageUrl("/assets/images/"+file.getOriginalFilename());
            	Dish result = dishService.save(dish);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert("dish", dish.getId().toString()))
                    .body(result);
                //return "You successfully uploaded " + file.getOriginalFilename() + "!";
            } catch (Exception e) {
              //return ResponseEntity.
            }
        } else {
           // return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
        }
		return null;
    }

}