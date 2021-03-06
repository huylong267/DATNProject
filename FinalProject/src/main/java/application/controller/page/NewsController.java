package application.controller.page;


import application.data.service.page.CategoryServiceImp;
import application.data.service.page.NewsServiceImp;
import application.model.NewsVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list-news")
public class NewsController {
    @Autowired
    private CategoryServiceImp categoryServiceImp;
    @Autowired
    private NewsServiceImp newsServiceImp;
    @GetMapping("/detail/news/{id}")
    public String detailNews(@PathVariable("id") int id, Model model) {
        model.addAttribute("categories", categoryServiceImp.findAllCate());
        model.addAttribute("news", newsServiceImp.findOneNews(id));
        return "blog_detail";
    }
}
