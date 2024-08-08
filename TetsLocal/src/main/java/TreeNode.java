import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;

@Data
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val,TreeNode left,TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}