// 全局配置
const AppConfig = {
  API_BASE_URL: 'http://localhost:8080/api',
  STORAGE_KEYS: {
    TOKEN: 'auth_token',
    USER_INFO: 'user_info'
  }
};

// DOM工具函数
const DOM = {
  // 获取元素
  get: (selector) => document.querySelector(selector),
  getAll: (selector) => document.querySelectorAll(selector),
  
  // 创建元素
  create: (tag, className = '') => {
    const element = document.createElement(tag);
    if (className) {
      element.className = className;
    }
    return element;
  },
  
  // 设置/获取元素文本
  text: (element, text = null) => {
    if (text === null) {
      return element.textContent;
    }
    element.textContent = text;
  },
  
  // 设置/获取元素HTML
  html: (element, html = null) => {
    if (html === null) {
      return element.innerHTML;
    }
    element.innerHTML = html;
  },
  
  // 清空元素内容
  empty: (element) => {
    while (element.firstChild) {
      element.removeChild(element.firstChild);
    }
  },
  
  // 添加事件监听
  on: (elements, event, callback) => {
    if (elements instanceof NodeList || Array.isArray(elements)) {
      elements.forEach(element => element.addEventListener(event, callback));
    } else {
      elements.addEventListener(event, callback);
    }
  },
  
  // 删除事件监听
  off: (elements, event, callback) => {
    if (elements instanceof NodeList || Array.isArray(elements)) {
      elements.forEach(element => element.removeEventListener(event, callback));
    } else {
      elements.removeEventListener(event, callback);
    }
  },
  
  // 添加CSS类
  addClass: (element, className) => {
    element.classList.add(className);
  },
  
  // 删除CSS类
  removeClass: (element, className) => {
    element.classList.remove(className);
  },
  
  // 切换CSS类
  toggleClass: (element, className) => {
    element.classList.toggle(className);
  },
  
  // 判断是否包含CSS类
  hasClass: (element, className) => {
    return element.classList.contains(className);
  },
  
  // 设置属性
  setAttr: (element, attr, value) => {
    element.setAttribute(attr, value);
  },
  
  // 获取属性
  getAttr: (element, attr) => {
    return element.getAttribute(attr);
  },
  
  // 删除属性
  removeAttr: (element, attr) => {
    element.removeAttribute(attr);
  },
  
  // 禁用按钮并显示加载状态
  disableButton: (button, loadingText = '加载中...') => {
    button.disabled = true;
    button.dataset.originalText = button.innerHTML;
    button.innerHTML = loadingText;
  },
  
  // 启用按钮并恢复原始文本
  enableButton: (button) => {
    button.disabled = false;
    if (button.dataset.originalText) {
      button.innerHTML = button.dataset.originalText;
    }
  }
};

// 本地存储工具
const Storage = {
  // 设置值
  set: (key, value) => {
    try {
      localStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error('Local storage error:', error);
    }
  },
  
  // 获取值
  get: (key, defaultValue = null) => {
    try {
      const value = localStorage.getItem(key);
      return value ? JSON.parse(value) : defaultValue;
    } catch (error) {
      console.error('Local storage error:', error);
      return defaultValue;
    }
  },
  
  // 删除值
  remove: (key) => {
    try {
      localStorage.removeItem(key);
    } catch (error) {
      console.error('Local storage error:', error);
    }
  },
  
  // 清除所有值
  clear: () => {
    try {
      localStorage.clear();
    } catch (error) {
      console.error('Local storage error:', error);
    }
  }
};

// 认证管理
const Auth = {
  // 保存令牌
  saveToken: (token) => {
    Storage.set(AppConfig.STORAGE_KEYS.TOKEN, token);
  },
  
  // 获取令牌
  getToken: () => {
    return Storage.get(AppConfig.STORAGE_KEYS.TOKEN);
  },
  
  // 检查是否已登录
  isLoggedIn: () => {
    return !!Auth.getToken();
  },
  
  // 清除令牌
  clearToken: () => {
    Storage.remove(AppConfig.STORAGE_KEYS.TOKEN);
  },
  
  // 保存用户信息
  saveUserInfo: (userInfo) => {
    Storage.set(AppConfig.STORAGE_KEYS.USER_INFO, userInfo);
  },
  
  // 获取用户信息
  getUserInfo: () => {
    const storedUserInfo = Storage.get(AppConfig.STORAGE_KEYS.USER_INFO);
    
    // 如果有完整的用户信息，直接返回
    if (storedUserInfo && storedUserInfo.username) {
      return storedUserInfo;
    }
    
    // 如果没有完整信息，尝试从本地token中提取或返回基本信息
    const username = localStorage.getItem('username') || '管理员';
    
    return {
      username: username,
      role: '管理员',
      // 其他默认信息
      avatar: null
    };
  },
  
  // 清除用户信息
  clearUserInfo: () => {
    Storage.remove(AppConfig.STORAGE_KEYS.USER_INFO);
  },
  
  // 登录 - 确保正确传递表单参数给后端@RequestParam
  login: async (username, password) => {
    try {
      // 直接传递表单参数，不包装在对象中
      const formData = new URLSearchParams();
      formData.append('username', username);
      formData.append('password', password);
      
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
      };
      
      const response = await fetch(API.buildUrl('/users/login'), options);
      const data = await API.handleResponse(response);
      
      if (data.code === 1) {
        // 保存令牌
        Auth.saveToken(data.data);
        
        // 获取用户信息
        const userInfoResponse = await API.get('/users/userInfo');
        if (userInfoResponse.code === 1) {
          Auth.saveUserInfo(userInfoResponse.data);
        }
        
        return { success: true, data: data.data };
      } else {
        return { success: false, message: data.message || '登录失败，请检查用户名和密码' };
      }
    } catch (error) {
      console.error('Login error:', error);
      return { success: false, message: '登录失败，请稍后重试' };
    }
  },
  
  // 注册 - 确保正确传递表单参数给后端@RequestParam
  register: async (username, password) => {
    try {
      // 直接传递表单参数，不包装在对象中
      const formData = new URLSearchParams();
      formData.append('username', username);
      formData.append('password', password);
      
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: formData
      };
      
      const response = await fetch(API.buildUrl('/users/register'), options);
      const data = await API.handleResponse(response);
      
      if (data.code === 1) {
        return { success: true, message: data.message || '注册成功' };
      } else {
        return { success: false, message: data.message || '注册失败' };
      }
    } catch (error) {
      console.error('Register error:', error);
      return { success: false, message: '注册失败，请稍后重试' };
    }
  },
  
  // 登出
  logout: () => {
    Auth.clearToken();
    Auth.clearUserInfo();
    window.location.href = 'login.html';
  }
};

// API请求工具
const API = {
  // 构建请求URL - 确保正确拼接基础URL和路径
  buildUrl: (path) => {
    // 确保path以/开头
    const formattedPath = path.startsWith('/') ? path : `/${path}`;
    return `${AppConfig.API_BASE_URL}${formattedPath}`;
  },
  
  // 构建请求选项
  buildOptions: (method, data = null, requiresAuth = true, contentType = 'application/x-www-form-urlencoded') => {
    const options = {
      method: method,
      headers: {
        'Content-Type': contentType
      }
    };
    
    // 添加认证令牌
    if (requiresAuth && Auth.isLoggedIn()) {
      options.headers['Authorization'] = `Bearer ${Auth.getToken()}`;
    }
    
    // 添加请求数据 - 根据contentType选择不同的处理方式
    if (data && method !== 'GET') {
      if (contentType === 'application/json') {
        options.body = JSON.stringify(data);
      } else {
        const formData = new URLSearchParams();
        // 确保每个参数都正确添加到formData中
        for (const key in data) {
          if (Object.prototype.hasOwnProperty.call(data, key)) {
            formData.append(key, data[key]);
          }
        }
        options.body = formData;
      }
    }
    
    return options;
  },
  
  // 处理响应
  handleResponse: async (response) => {
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    try {
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('JSON parse error:', error);
      throw new Error('Invalid response format');
    }
  },
  
  // GET请求
  get: async (path, requiresAuth = true) => {
    const url = API.buildUrl(path);
    const options = API.buildOptions('GET', null, requiresAuth);
    
    try {
      const response = await fetch(url, options);
      return API.handleResponse(response);
    } catch (error) {
      console.error('API GET error:', error);
      throw error;
    }
  },
  
  // POST请求
  post: async (path, data = null, requiresAuth = true, contentType = 'application/x-www-form-urlencoded') => {
    const url = API.buildUrl(path);
    const options = API.buildOptions('POST', data, requiresAuth, contentType);
    
    try {
      const response = await fetch(url, options);
      return API.handleResponse(response);
    } catch (error) {
      console.error('API POST error:', error);
      throw error;
    }
  },
  
  // PUT请求
  put: async (path, data = null, requiresAuth = true, contentType = 'application/x-www-form-urlencoded') => {
    const url = API.buildUrl(path);
    const options = API.buildOptions('PUT', data, requiresAuth, contentType);
    
    try {
      const response = await fetch(url, options);
      return API.handleResponse(response);
    } catch (error) {
      console.error('API PUT error:', error);
      throw error;
    }
  },
  
  // PATCH请求
  patch: async (path, data = null, requiresAuth = true) => {
    const url = API.buildUrl(path);
    const options = API.buildOptions('PATCH', data, requiresAuth);
    
    try {
      const response = await fetch(url, options);
      return API.handleResponse(response);
    } catch (error) {
      console.error('API PATCH error:', error);
      throw error;
    }
  },
  
  // DELETE请求
  delete: async (path, requiresAuth = true) => {
    const url = API.buildUrl(path);
    const options = API.buildOptions('DELETE', null, requiresAuth);
    
    try {
      const response = await fetch(url, options);
      return API.handleResponse(response);
    } catch (error) {
      console.error('API DELETE error:', error);
      throw error;
    }
  }
};

// 表单验证工具
const Validation = {
  // 验证用户名
  username: (username) => {
    const pattern = /^[a-zA-Z0-9_]{3,20}$/;
    return {
      valid: pattern.test(username),
      message: '用户名需为3-20位字母、数字或下划线组合'
    };
  },
  
  // 验证密码
  password: (password) => {
    // 密码长度至少8位，包含字母和数字
    const pattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return {
      valid: pattern.test(password),
      message: '密码长度至少8位，且包含字母和数字'
    };
  },
  
  // 验证邮箱
  email: (email) => {
    const pattern = /^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/;
    return {
      valid: pattern.test(email),
      message: '请输入有效的邮箱地址'
    };
  },
  
  // 验证手机号
  phone: (phone) => {
    const pattern = /^1[3-9]\d{9}$/;
    return {
      valid: pattern.test(phone),
      message: '请输入有效的手机号码'
    };
  },
  
  // 验证两个值是否相同
  confirm: (value, confirmValue) => {
    return {
      valid: value === confirmValue,
      message: '两次输入不相同'
    };
  }
};

// 提示框工具
const Toast = {
  // 创建提示框元素
  createElement: (message, type = 'info') => {
    const toast = DOM.create('div', `toast toast-${type}`);
    toast.textContent = message;
    document.body.appendChild(toast);
    return toast;
  },
  
  // 显示提示框
  show: (message, type = 'info', duration = 3000) => {
    const toast = Toast.createElement(message, type);
    
    // 显示提示框
    setTimeout(() => {
      DOM.addClass(toast, 'show');
    }, 10);
    
    // 自动隐藏提示框
    setTimeout(() => {
      DOM.removeClass(toast, 'show');
      setTimeout(() => {
        document.body.removeChild(toast);
      }, 300);
    }, duration);
    
    return toast;
  },
  
  // 成功提示
  success: (message, duration = 3000) => {
    return Toast.show(message, 'success', duration);
  },
  
  // 错误提示
  error: (message, duration = 3000) => {
    return Toast.show(message, 'error', duration);
  },
  
  // 信息提示
  info: (message, duration = 3000) => {
    return Toast.show(message, 'info', duration);
  }
};

// 工具函数
const Utils = {
  // 防抖函数
  debounce: (func, wait) => {
    let timeout;
    return function executedFunction(...args) {
      const later = () => {
        clearTimeout(timeout);
        func(...args);
      };
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
    };
  },
  
  // 节流函数
  throttle: (func, limit) => {
    let inThrottle;
    return function(...args) {
      if (!inThrottle) {
        func(...args);
        inThrottle = true;
        setTimeout(() => (inThrottle = false), limit);
      }
    };
  },
  
  // 生成随机ID
  generateId: () => {
    return Date.now().toString(36) + Math.random().toString(36).substr(2);
  },
  
  // 格式化日期
  formatDate: (date, format = 'YYYY-MM-DD HH:mm:ss') => {
    const d = new Date(date);
    
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hours = String(d.getHours()).padStart(2, '0');
    const minutes = String(d.getMinutes()).padStart(2, '0');
    const seconds = String(d.getSeconds()).padStart(2, '0');
    
    return format
      .replace('YYYY', year)
      .replace('MM', month)
      .replace('DD', day)
      .replace('HH', hours)
      .replace('mm', minutes)
      .replace('ss', seconds);
  },
  
  // 验证是否为数字
  isNumber: (value) => {
    return !isNaN(value) && isFinite(value);
  },
  
  // 验证是否为整数
  isInteger: (value) => {
    return Number.isInteger(Number(value));
  },
  
  // 验证是否为空
  isEmpty: (value) => {
    return value === null || value === undefined || value === '';
  },
  
  // 验证是否为对象
  isObject: (value) => {
    return value !== null && typeof value === 'object' && Array.isArray(value) === false;
  },
  
  // 验证是否为数组
  isArray: (value) => {
    return Array.isArray(value);
  }
};

// 初始化应用
function initApp() {
  // 检查登录状态
  if (Auth.isLoggedIn()) {
    // 如果用户已登录，但当前页面是登录或注册页面，则重定向到首页
    const currentPath = window.location.pathname;
    if (currentPath.includes('login.html') || currentPath.includes('register.html')) {
      window.location.href = 'dashboard.html';
    }
  } else {
    // 如果用户未登录，但当前页面不是登录或注册页面，则重定向到登录页面
    const currentPath = window.location.pathname;
    if (!currentPath.includes('login.html') && !currentPath.includes('register.html')) {
      window.location.href = 'login.html';
    }
  }
}

// 导出模块（如果支持）
if (typeof module !== 'undefined' && module.exports) {
  module.exports = {
    AppConfig,
    DOM,
    Storage,
    Auth,
    API,
    Validation,
    Toast,
    Utils,
    initApp
  };
}