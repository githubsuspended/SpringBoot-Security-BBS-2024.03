package com.example.security.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.security.entity.SecurityUser;
import com.example.security.service.SecurityUserService;
import com.example.security.util.AsideUtil;
import com.example.security.util.ImageUtil;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class SecurityUserController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired private SecurityUserService securityService;
	@Autowired private BCryptPasswordEncoder bCryptEncoder;
	@Autowired private ResourceLoader resourceLoader;
	@Autowired private AsideUtil asideUtil;
	@Autowired private ImageUtil imageUtil;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	private String menu = "user";

	@GetMapping({"/list/{page}", "/list"})
	public String list(@PathVariable(required=false) Integer page, HttpSession session, Model model) {
		page = (page == null) ? 1 : page;
		session.setAttribute("currentUserPage", page);
		List<SecurityUser> list = securityService.getSecurityUserList(page);
		model.addAttribute("userList", list);
		
		// for pagination
		int totalUsers = securityService.getSecurityUserCount();
		int totalPages = (int) Math.ceil(totalUsers * 1.0 / SecurityUserService.COUNT_PER_PAGE);
		List<Integer> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(i);
		model.addAttribute("pageList", pageList);
		model.addAttribute("menu", menu);
		return "user/list";
	}
	
	@GetMapping("/login") 
	public String loginForm() {
		return "user/login";
	}
	
	@GetMapping("/loginSuccess")
	public String loginSuccess(HttpSession session, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 세션의 현재 사용자 아이디
		String uid = authentication.getName();
		// 세션의 현재 사용자 role
//		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
//		GrantedAuthority auth = iter.next();
//		String role = auth.getAuthority();
		
		SecurityUser secUser = securityService.findByUid(uid);
		session.setAttribute("sessSuid", secUser.getSuid());
		session.setAttribute("sessUid", uid);
		session.setAttribute("sessUname", secUser.getUname());
		session.setAttribute("profile", secUser.getProfile());
		session.setAttribute("provider", secUser.getProvider());
		session.setAttribute("role", secUser.getRole());
		session.setAttribute("email", secUser.getEmail());
		session.setAttribute("github", secUser.getGithub() == null ? "Github Id" : secUser.getGithub());
		session.setAttribute("insta", secUser.getInsta() == null ? "Insta Id" : secUser.getInsta());
		session.setAttribute("location", secUser.getLocation() == null ? "경기도 수원시 팔달구" : secUser.getLocation());
		
		// 상태 메세지
		Resource resource = resourceLoader.getResource("classpath:/static/data/todayQuote.txt");
		String quoteFile = null;
		try {
			quoteFile = resource.getURI().getPath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String stateMsg = asideUtil.getTodayQuote(quoteFile);
		session.setAttribute("stateMsg", stateMsg);
		// 환영 메세지
		log.info("Info Login: {}, {}", uid, secUser.getUname());
		model.addAttribute("msg", secUser.getUname()+"님 환영합니다.");
		model.addAttribute("url", "/sbbs/board/list");
		return "common/alertMsg";
	}
	
	@GetMapping("/register")
	public String registerForm() {
		return "user/register";
	}
	
	@PostMapping("/register")
	public String registerProc(MultipartHttpServletRequest req, Model model,
			String uid, String pwd, String pwd2, String uname, String email,
			String github, String insta, String location) {
		String filename = null;
		MultipartFile filePart = req.getFile("profile");
		
		SecurityUser secUser = securityService.findByUid(uid);
		if (secUser != null) {
			model.addAttribute("msg", "사용자 ID가 중복되었습니다.");
			model.addAttribute("url", "/sbbs/user/register");
			return "common/alertMsg";
		}
		if (pwd == null || !pwd.equals(pwd2)) {
			model.addAttribute("msg", "패스워드 입력이 잘못되었습니다.");
			model.addAttribute("url", "/abbs/user/register");
			return "common/alertMsg";
		}
		if (filePart.getContentType().contains("image")) {
			filename = filePart.getOriginalFilename();
			String path = uploadDir + "profile/" + filename;
			try {
				filePart.transferTo(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
			}
			filename = imageUtil.squareImage(uid, filename);
		}
		String hashedPwd = bCryptEncoder.encode(pwd);
		secUser = new SecurityUser(uid, hashedPwd, uname, email, "ck world", 
				"/sbbs/file/download/profile/"+filename, github, insta, location);
		securityService.insertSecurityUser(secUser);
		model.addAttribute("msg", "등록을 마쳤습니다. 로그인하세요.");
		model.addAttribute("url", "/sbbs/user/login");
		return "common/alertMsg";
	}
	
	@ResponseBody
	@GetMapping("/detail/{uid}")
	public String detail(@PathVariable String uid) {
		SecurityUser secUser = securityService.findByUid(uid);
		JSONObject jUser = new JSONObject();
		jUser.put("suid", secUser.getSuid());
		jUser.put("uid", uid);
		jUser.put("pwd", secUser.getPwd());
		jUser.put("uname", secUser.getUname());
		jUser.put("email", secUser.getEmail());
		jUser.put("provider", secUser.getProvider());
		jUser.put("role", secUser.getRole());
		jUser.put("profile", secUser.getProfile());
		jUser.put("github", secUser.getGithub());
		jUser.put("insta", secUser.getInsta());
		jUser.put("location", secUser.getLocation());
		return jUser.toString();
	}

	@PostMapping("/update")
	public String update(int suid, String uid, String pwd, String pwd2, String uname, String email,
			String github, String insta, String location, String hashedPwd, String profile,
			MultipartHttpServletRequest req, HttpSession session, Model model) {
		String filename = null;
		SecurityUser secUser = null;
		int currentUserPage = (Integer) session.getAttribute("currentUserPage");
		MultipartFile filePart = req.getFile("newProfile");
		String sessUid = (String) session.getAttribute("sessUid");
		String provider = (String) session.getAttribute("provider");

		if (!sessUid.equals(uid)) {
			model.addAttribute("msg", "수정 권한이 없습니다.");
			model.addAttribute("url", "/sbbs/user/list/" + currentUserPage);
			return "common/alertMsg";
		}
		if (provider.equals("ck world")) { 			// Local 등록 사용자
			if (pwd != null && pwd.length() > 1 && pwd.equals(pwd2))
				hashedPwd = bCryptEncoder.encode(pwd);
			if (filePart.getContentType().contains("image")) {
				// 기존 사진 지우기
				int idx = profile.lastIndexOf("/");
				String path = uploadDir + "profile/" + profile.substring(idx + 1);
				File file = new File(path);
				file.delete();
				
				filename = filePart.getOriginalFilename();
				path = uploadDir + "profile/" + filename;
				try {
					filePart.transferTo(new File(path));
				} catch (Exception e) {
					e.printStackTrace();
				}
				profile = "/sbbs/file/download/profile/" + imageUtil.squareImage(uid, filename);
			}
			secUser = new SecurityUser(suid, uid, hashedPwd, uname, email, profile, github, insta, location);
			session.setAttribute("sessUname", uname);
			session.setAttribute("profile", profile);
			session.setAttribute("email", email);
		} else {			// Social Loginner
			secUser = securityService.findBySuid(suid);
			secUser.setGithub(github); secUser.setInsta(insta); secUser.setLocation(location);
		}
		securityService.updateSecurityUser(secUser);
		session.setAttribute("github", github);
		session.setAttribute("insta", insta);
		session.setAttribute("location", location);
		return "redirect:/user/list/" + currentUserPage;
	}
	
	@GetMapping("/delete/{uid}")
	public String delete(@PathVariable String uid, HttpSession session, Model model) {
		String sessUid = (String) session.getAttribute("sessUid");
		String provider = (String) session.getAttribute("provider");
		String role = (String) session.getAttribute("role");
		if (role.equals("ROLE_ADMIN")) {
			securityService.deleteSecurityUser(uid);
			return "redirect:/user/list";
		} else if (provider.equals("ck world") && sessUid.equals(uid)) {
			securityService.deleteSecurityUser(uid);
			session.invalidate();
			return "redirect:/user/login";
		} else {
			model.addAttribute("msg", "삭제 권한이 없습니다.");
			model.addAttribute("url", "/abbs/user/list");
			return "common/alertMsg";
		}
	}
	
}
