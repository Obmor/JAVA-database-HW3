package Impl;

import model.Avatar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.AvatarRepository;
import repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class AvatarServiceImpl  {
    public class AvatarService {

        private final AvatarRepository avatarRepository;
        private final StudentRepository studentRepository;
        private final Path avatarsDir;

        public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, @Value("${avatars.dir}") Path avatarsDir) {
            this.avatarRepository = avatarRepository;
            this.studentRepository = studentRepository;
            this.avatarsDir = avatarsDir;
        }

        public Avatar save(Long studentId, MultipartFile file) throws IOException {
            Files.createDirectories(avatarsDir);
            var index = file.getOriginalFilename().lastIndexOf('.');
            var extension = file.getOriginalFilename().substring(index);
            Path filePath = avatarsDir.resolve(studentId + extension);
            try (var in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            Avatar avatar = avatarRepository.findAllByStudentId(studentId).orElse(new Avatar());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            avatar.setStudent(studentRepository.getReferenceById(studentId));
            avatar.setFilePath(filePath.toString());
            return avatarRepository.save(avatar);
        }

        public Avatar getById(Long id) {
            return avatarRepository.findById(id).orElse(null);
        }

        public Page<Avatar> findAll(Pageable pageable) {
            return avatarRepository.findAll(pageable);
        }
    }
}
