package application.controller.admin;

import application.data.model.Category;
import application.data.model.Product;
import application.data.service.page.CategoryServiceImp;
import application.data.service.page.ProductServiceImp;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class AdminCategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @Autowired
    private ProductServiceImp  productServiceImp;

    @GetMapping("/admin/category")
    public String category(Model model){
        model.addAttribute("listcate",categoryServiceImp.findAllCate());
        return "admin/category_list";
    }

    @GetMapping("/admin/category/add")
    public String categoryAdd(Model model){
         model.addAttribute("category", new Category() );
         return "admin/category_form";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

    @PostMapping("/admin/category/save")
    public String categorySave(@Valid Category category, BindingResult result , RedirectAttributes redirect){


        if(result.hasErrors()){
            System.out.println("Loi");
            return "admin/category_form";
        }else if(category.getCreated_date()==null){
                category.setCreated_date(new Date());
                category.setUpdated_date(new Date());
                categoryServiceImp.saveCate(category);
                redirect.addFlashAttribute("success","Lưu thành công danh mục sản phẩm");
                return "redirect:/admin/category/";
            }else if (category.getCreated_date() !=null){
                category.setUpdated_date(new Date());
                categoryServiceImp.saveCate(category);
                redirect.addFlashAttribute("success","Lưu thành công danh mục sản phẩm");
                return "redirect:/admin/category/";
            }

                return "redirect:/admin/category/";
        }


    @GetMapping("/admin/category/{id}/edit")
    public String editCate(@PathVariable("id") Integer id,Model model){
        Category category = categoryServiceImp.findOneCate(id);
        model.addAttribute("category", category);
        return "admin/category_form";
    }

    @GetMapping("/admin/category/{id}/delete")
    public String deleteCate(@PathVariable("id") Integer id , RedirectAttributes redirectAttributes){

        List<Product> products = productServiceImp.listproductBycategory(id);
        if(products.size() >0 ){
            redirectAttributes.addFlashAttribute("success","Có sản phẩm thuộc danh mục này");
        }else {
            categoryServiceImp.deleteCate(id);
            redirectAttributes.addFlashAttribute("success","Xóa danh mục sản phẩm thành công");
        }

        return "redirect:/admin/category/";
    }

}
