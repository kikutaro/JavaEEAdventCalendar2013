package jp.co.hoge.member.registration.flow;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.flow.FlowScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;


/**
 * 会員登録ページの管理対象クラス.
 * @author kikutaro
 */
@Named(value = "memberRegistrationBean")
@FlowScoped("registrationFlow")
public class MemberRegistrationBean implements Serializable{
    
    @Getter @Setter
    private String lastName;

    @Getter @Setter
    private String firstName;
    
    @Getter @Setter
    private String code;
    
    public void startMemberRegistration(){
        System.out.println("会員登録フローの開始");
    }
    
    public void endMemberRegistration(){
        System.out.println("会員登録フローの終了");
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        } catch (IOException ex) {
            
        }
    }
    
    public String check(){
        Map flowObject = FacesContext.getCurrentInstance().getApplication().getFlowHandler().getCurrentFlowScope();
        System.out.println(flowObject.get("age"));
        return "message";
    }
}
