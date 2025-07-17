package product_service.service;

import org.springframework.stereotype.Service;
import product_service.baseService.BaseServiceImpl;
import product_service.model.Tag;
import product_service.repository.TagRepository;

@Service
public class TagService extends BaseServiceImpl<Tag, Long> {

    public TagService(TagRepository tagRepository){
        super(tagRepository);
    }
}
