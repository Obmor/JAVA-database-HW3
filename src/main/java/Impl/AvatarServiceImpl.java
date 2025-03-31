package Impl;

import model.Avatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AvatarServiceImpl {
    public class AvatarService {

        private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);

        private final AvatarRepository avatarRepository;
        private final StudentRepository studentRepository;
        private final Path avatarsDir;

        public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, @Value("${avatars.dir}") Path avatarsDir) {
            this.avatarRepository = avatarRepository;
            this.studentRepository = studentRepository;
            this.avatarsDir = avatarsDir;
        }

        public Avatar save(Long studentId, MultipartFile file) throws IOException {
            logger.info("It was invoked method for save avatar for studentId: {}", studentId);
            Files.createDirectories(avatarsDir);
            var index = file.getOriginalFilename().lastIndexOf('.');
            var extension = file.getOriginalFilename().substring(index);
            Path filePath = avatarsDir.resolve(studentId + extension);

            logger.debug("Saving file to path: {}", filePath);
            try (var in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                logger.error("Error saving file for studentId: {}", studentId, e);
                throw e;
            }

            Avatar avatar = avatarRepository.findAllByStudentId(studentId).orElse(new Avatar());
            avatar.setFileSize(file.getSize());
            avatar.setMediaType(file.getContentType());
            avatar.setData(file.getBytes());
            avatar.setStudent(studentRepository.getReferenceById(studentId));
            avatar.setFilePath(filePath.toString());
            logger.info("Saving avatar for studentId: {}", studentId);
            return avatarRepository.save(avatar);
        }

        public Avatar getById(Long id) {
            logger.info("It was invoked method for get avatar by id: {}", id);
            return avatarRepository.findById(id).orElse(null);
        }

        public Page<Avatar> findAll(Pageable pageable) {
            logger.info("It was invoked method for find all avatars with pageable: {}", pageable);
            return avatarRepository.findAll(pageable);
        }
    }
}
