package application.data.service.page;

import application.data.model.PaginableItemList;
import application.data.model.Product;
import application.data.repository.web.iProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements iProductService {
    @Autowired
    private iProductRepository iProductRepository;
    @Override
    public void addNewProduct(Product product) {
        iProductRepository.save(product);
    }

    @Override
    public Product findOneProduct(int productId) {
        return iProductRepository.findOne(productId);
    }

    @Override
    public List<Product> findAllProduct() {
        return iProductRepository.findAll();
    }

    @Override
    public void updateproduct(Product product) {
        iProductRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
            iProductRepository.delete(productId);

    }

    @Override
    public List<Product> findbyName(String name) {
        return iProductRepository.findByNameContaining(name);
    }

    @Override
    public List<Product> listproductBycategory(int categoryId) {
        return iProductRepository.listproductBycategory(categoryId);
    }

    @Override
    public List<Product> listProductHome(int categoryId) {
        return iProductRepository.listProductHome(categoryId);
    }

    public PaginableItemList<Product> getListProducts(int pageSize, int pageNumber) {

        PaginableItemList<Product> paginableItemList = new PaginableItemList<>();
        paginableItemList.setPageSize(pageSize);
        paginableItemList.setPageNumber(pageNumber);

        Page<Product> pages = iProductRepository.findAll(new PageRequest(pageNumber, pageSize));
        paginableItemList.setTotalProducts(pages.getTotalElements());
        paginableItemList.setListData(pages.getContent());
        return paginableItemList;
    }



    public PaginableItemList<Product> getListProductPaging (int pageSize,int pageNumber, int categoryId){
        PaginableItemList<Product> paginableItemList = new PaginableItemList<>();
        paginableItemList.setPageSize(pageSize);
        paginableItemList.setPageNumber(pageNumber);
        Page<Product> pages = iProductRepository.listProductPaging(categoryId,new PageRequest(pageNumber,pageSize));
        paginableItemList.setTotalProducts(pages.getTotalElements());
        paginableItemList.setListData(pages.getContent());
        return paginableItemList;
    }
    public PaginableItemList<Product> getListProductPricePaging (int pageSize,int pageNumber, int priceMin,int priceMax){
        PaginableItemList<Product> paginableItemList = new PaginableItemList<>();
        paginableItemList.setPageSize(pageSize);
        paginableItemList.setPageNumber(pageNumber);
        Page<Product> pages = iProductRepository.listProductPricePaging(priceMax,priceMin,new PageRequest(pageNumber,pageSize));
        paginableItemList.setTotalProducts(pages.getTotalElements());
        paginableItemList.setListData(pages.getContent());
        return paginableItemList;
    }
}
