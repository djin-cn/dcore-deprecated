/**
 * 
 */
package me.djin.dcore.frame.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

import me.djin.dcore.exception.ApplicationException;
import me.djin.dcore.rbac.CurrentUser;
import me.djin.dcore.util.RsaUtils;

/**
 * @author djin 令牌模型
 */
public class TokenModel {
	/**
	 * 当前JAR包所在目录
	 */
	private static final String JAR_PATH = TokenModel.class.getProtectionDomain().getCodeSource().getLocation()
			.getPath();
	/**
	 * 私钥文件
	 */
	private static final String PRIVATE_FILE = JAR_PATH + "token_private.key";
	/**
	 * 公钥文件
	 */
	private static final String PUBLIC_FILE = JAR_PATH + "token_public.key";
	
	/**
	 * 有效期，7*24小时
	 */
	private static final long LIFE_TIMEMILLIS = 7 * 24 * 60 * 60 * 1000L;

	private CurrentUser user;
	private Date expire;
	
	/**
	 * 默认Token有效期为7*24小时
	 */
	public TokenModel() {
		setExpire(new Date(System.currentTimeMillis() + LIFE_TIMEMILLIS));
	}
	
	public TokenModel(CurrentUser user) {
		setExpire(new Date(System.currentTimeMillis() + LIFE_TIMEMILLIS));
		setUser(user);
	}

	/**
	 * 数据，一般为脱敏的用户信息
	 * 
	 * @return
	 */
	public CurrentUser getUser() {
		return user;
	}

	/**
	 * 数据，一般为脱敏的用户信息
	 * 
	 * @param user
	 */
	public void setUser(CurrentUser user) {
		this.user = user;
	}

	/**
	 * 令牌过期时间
	 * 
	 * @return
	 */
	public Date getExpire() {
		return expire;
	}

	/**
	 * 令牌过期时间
	 * 
	 * @param expire
	 */
	public void setExpire(Date expire) {
		this.expire = expire;
	}

	/**
	 * 生成token
	 */
	@Override
	public String toString() {
		try {
			RsaUtils.generateKeys(PUBLIC_FILE, PRIVATE_FILE);
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new ApplicationException(e, this);
		}
		String text = JSON.toJSONString(this);
		byte[] bytes = null;
		try {
			bytes = RsaUtils.encrypt(PRIVATE_FILE, text);
		} catch (InvalidKeyException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			throw new ApplicationException(e, this);
		}
		String token = Base64.encodeBase64String(bytes);
		return token;
	}
	
	/**
	 * 将令牌解密
	 * @param token
	 * @return
	 * @throws InvalidKeyException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public static TokenModel parse(String token) throws InvalidKeyException, FileNotFoundException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		byte[] bytes = RsaUtils.decrypt(TokenModel.PUBLIC_FILE, Base64.decodeBase64(token));
		String text = new String(bytes);
		TokenModel model = JSON.parseObject(text, TokenModel.class);
		return model;
	}
}
