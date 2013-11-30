package jp.co.hoge.member.registration.flow;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowBuilderParameter;
import javax.faces.flow.builder.FlowDefinition;
import javax.inject.Named;
import lombok.Getter;

/**
 * メンバー登録フロー定義クラス.
 * @author kikutaro
 */
@Named
@Dependent
public class MemberRegistrationFlowDefinition {
    
    //本クラスで定義するフローのID
    @Getter
    private final String flowId = "registrationFlow";
    
    @Produces
    @FlowDefinition
    public Flow defineFlow(@FlowBuilderParameter FlowBuilder flowBuilder){
        
        //Flowの最初に処理を呼び出されるinitializer
        flowBuilder.initializer("#{memberRegistrationBean.startMemberRegistration()}");
        
        //FlowのIDを定義する
        flowBuilder.id("", flowId);
        
        //戻るノード
        flowBuilder.returnNode("index").fromOutcome("/index");
        
        //ノードの始点を定義
        flowBuilder.viewNode(flowId, "/registration/regFirst.xhtml").markAsStartNode();
        
        //メッセージ入力ページ
        flowBuilder.viewNode("message", "/registration/regSecond.xhtml");
        
        //登録確認ページ
        flowBuilder.viewNode("confirm", "/registration/regThird.xhtml");
        
        //メソッド呼び出し
        flowBuilder.methodCallNode("test").expression("#{memberRegistrationBean.check()}");
        
        //条件によるフローのスイッチ
        flowBuilder.switchNode("skip").defaultOutcome("confirm")
                .switchCase()
                .condition("#{memberRegistrationBean.lastName eq 'kikuta'}")
                .fromOutcome("message");
        
        //別フローへの遷移
        flowBuilder.flowCallNode("privilege")
                .flowReference("", "privilegesFlow")
                .outboundParameter("firstName", "#{memberRegistrationBean.firstName}");
        
        //フローから戻り
        flowBuilder.inboundParameter("code", "#{memberRegistrationBean.code}");
        
        //Flowの最後に処理を呼び出されるfinalizer
        flowBuilder.finalizer("#{memberRegistrationBean.endMemberRegistration()}");
        
        return flowBuilder.getFlow();
    }
}
