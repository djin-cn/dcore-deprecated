package me.djin.dcore.rbac;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.djin.dcore.exception.ApplicationException;
import me.djin.dcore.frame.model.TokenModel;
import me.djin.dcore.util.bean.BeanUtils;

/**
 * 无状态Subject
 * 
 * @author djin
 *
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {
	/**
	 * 头部令牌键
	 */
	public static final String TOKEN_HEADER = "TOKEN";
	private static final Logger LOGGER = LoggerFactory.getLogger(StatelessDefaultSubjectFactory.class);

	@Override
	public Subject createSubject(SubjectContext context) {
		LOGGER.trace("StatelessDefaultSubjectFactory:无状态SESSION");
		context.setSessionCreationEnabled(false);
		if (!(context instanceof WebSubjectContext)) {
			return super.createSubject(context);
		}
		WebSubjectContext wsc = (WebSubjectContext) context;
		initSubjectContext(wsc);
		SecurityManager securityManager = wsc.resolveSecurityManager();
		Session session = wsc.resolveSession();
		boolean sessionEnabled = wsc.isSessionCreationEnabled();
		PrincipalCollection principals = wsc.resolvePrincipals();
		boolean authenticated = wsc.resolveAuthenticated();
		String host = wsc.resolveHost();
		ServletRequest request = wsc.resolveServletRequest();
		ServletResponse response = wsc.resolveServletResponse();
		return new WebDelegatingSubject(principals, authenticated, host, session, sessionEnabled, request, response,
				securityManager);
	}

	/**
	 * 初始化Subject上下文，在此处根据用户令牌初始化登录用户
	 * @param context
	 */
	protected void initSubjectContext(WebSubjectContext context) {
		HttpServletRequest request = (HttpServletRequest)context.getServletRequest();
		if(request == null) {
			return;
		}
		String text = request.getHeader(StatelessDefaultSubjectFactory.TOKEN_HEADER);
		if(StringUtils.isBlank(text)) {
			return;
		}
		TokenModel model = null;
		try {
			model = TokenModel.parse(text);
		} catch (InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			throw new ApplicationException(e, text);
		}
		
		AuthenticationUser user = null;
		try {
			user = BeanUtils.copy(model.getUser(), AuthenticationUser.class);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ApplicationException(e, model);
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(""), (new MyShiroRealm()).getName()); 
		context.setAuthenticationInfo(info);
//		context.setAuthenticated(true);
		
		AuthenticationToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		context.setAuthenticationToken(token);
	}
}