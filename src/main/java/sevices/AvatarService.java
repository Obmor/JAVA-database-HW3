package sevices;

import model.Avatar;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    Avatar save(Long studentId, MultipartFile file);

    Avatar getById(Long id);

    Avatar findAll(Pageable pageable);
}
