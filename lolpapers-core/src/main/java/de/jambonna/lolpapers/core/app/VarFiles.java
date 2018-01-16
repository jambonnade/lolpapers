package de.jambonna.lolpapers.core.app;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import net.jcip.annotations.ThreadSafe;

/**
 * Utility to manipulate files and directories generated by the app
 */
@ThreadSafe
public class VarFiles {
    private final FileSystem fs;
    private final Path varPath;
    private final Path privatePath;
    private final Path pubPath;
    
    
    public VarFiles(FileSystem fs, String varPath) throws AppException {
        this.fs = fs;
        try {
            this.varPath = fs.getPath(varPath).toRealPath();
            privatePath = this.varPath.resolve("resources");
            pubPath = this.varPath.resolve("pub");
        } catch (Exception e) {
            throw new AppException("Unable to create var files object", e);
        }
    }
    
    public Path getVarPath() {
        return varPath;
    }
    
    public Path getFinalFilePath(Path relativePath, boolean pub) {
        Path root = pub ? pubPath : privatePath;
        Path p = root.resolve(relativePath).normalize();
        if (!p.startsWith(root)) {
            throw new IllegalArgumentException("Relative path outside of root");
        }
        return p;
    }
    
    public void prepareDirectories(Path fullDirPath) throws IOException {
        fullDirPath = fullDirPath.normalize();
        if (!fullDirPath.startsWith(varPath) 
                || !fs.equals(fullDirPath.getFileSystem())) {
            throw new IllegalArgumentException("Only for var paths");
        }
        if (!Files.isDirectory(varPath)) {
            // This is to avoid creating the var path itself and parents, 
            // it has to be pre-created
            throw new IllegalStateException("Var path is not a directory");
        }
        
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx");
        FileAttribute<Set<PosixFilePermission>> subDirsPermissions = PosixFilePermissions.asFileAttribute(perms);

        Files.createDirectories(fullDirPath, subDirsPermissions);
    }
}