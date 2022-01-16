package com.panadora.pandora.service.item;

import com.panadora.pandora.model.entities.collection.item.Chapter;
import com.panadora.pandora.model.entities.collection.subitem.FileType;
import com.panadora.pandora.model.entities.collection.subitem.Page;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class ChapterPagesList {
    private final String FILE_SEPARATOR = FileSystems.getDefault().getSeparator();
    public void listPages(Chapter chapter) throws IOException {
        String path = chapter.getPath();
        Stream<Path> files  = Files.list(Paths.get(path));
        files.filter(file -> file.endsWith(FileType.PNG.getValue()) || file.endsWith(FileType.JPG.getValue())).map(file ->{
            try {
              Page page = new Page();
              long size = Files.size(file);
              page.setBytesLength(size);
              if(file.endsWith(FileType.PNG.getValue())){
                  page.setFileType(FileType.PNG);
              }else if(file.endsWith(FileType.JPG.getValue())){
                  page.setFileType(FileType.JPG);
              }
              page.setChapter(chapter);
              page.setPath(path + FILE_SEPARATOR +  file.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  new Page();
        });
    }
}
