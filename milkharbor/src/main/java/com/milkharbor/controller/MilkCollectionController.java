package com.milkharbor.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milkharbor.entity.MilkCollection;
import com.milkharbor.service.MilkCollectionService;

@CrossOrigin
@RestController
@RequestMapping("milkCollection")
public class MilkCollectionController {
	
	@Autowired
	private MilkCollectionService milkCollectionService;
	
	@PostMapping("/collect")
	public boolean collectMilk(@RequestBody MilkCollection milkCollection) throws ParseException {
		return milkCollectionService.collectMilk(milkCollection);
	}
	
	@GetMapping("/get")
	public List<MilkCollection> getMilkCollectionDetails(){
		return milkCollectionService.getMilkCollectionDetails();
	}
	
	@GetMapping("/get/{id}")
	public List<MilkCollection> getSupplyMilkDetails(@PathVariable int id){
		return milkCollectionService.getSupplyMilkDetails(id);
	}
	
	@PutMapping("/update")
	public boolean updateMilkCollection(@RequestBody MilkCollection milkCollection) {
		return milkCollectionService.updateMilkCollection(milkCollection);
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteMIlkDetails(@PathVariable int id) {
		return milkCollectionService.deleteMIlkDetails(id);
	}
	
	@GetMapping("/report")
    public ResponseEntity<byte[]> generateReport() throws IOException {
        List<MilkCollection> data = milkCollectionService.getMilkCollectionDetails();
        String templateContent = getTemplateContent(data);
        byte[] reportBytes = templateContent.getBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "report.html");

        return ResponseEntity.ok()
                .headers(headers)
                .body(reportBytes);
    }

    private String getTemplateContent(List<MilkCollection> data) {
        // Generate Thymeleaf template content as a string
        // You can use a Thymeleaf template engine or simply concatenate strings here
        // For simplicity, we'll use a basic approach for this example
        StringBuilder templateBuilder = new StringBuilder();
        templateBuilder.append("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Milk Collection Report</title></head><body>");
        templateBuilder.append("<h1>Report</h1><table><thead><tr><th>Milk Collection Id</th><th>Farmer Id</th><th>Milk Fat</th><th>Milk Quantity</th><th>Price Per Liter</th><th>Total bill</th><th>Milk Lactometer</th><th>Date and Time</th><th>Milk Snf</th></tr></thead><tbody>");
        for (MilkCollection entity : data) {
            templateBuilder.append("<tr><td>").append(entity.getM_id()).append("</td><td>").append(entity.getF_id()).append("</td><td>").append(entity.getMilk_fat()).append("</td><td>").append(entity.getMilk_qnt()).append("</td><td>").append(entity.getPrice_per_liter()).append("</td><td>").append(entity.getTotal()).append("</td><td>").append(entity.getMilk_lac_deg()).append("</td><td>").append(entity.getDate_time()).append("</td><td>").append(entity.getMilk_snf()).append("<td></tr>");
        }
        templateBuilder.append("</tbody></table></body></html>");
        return templateBuilder.toString();
    }

}
