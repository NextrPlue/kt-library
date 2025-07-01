// API 기본 설정
const API_CONFIG = {
  gateway: 'https://8088-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io',
  author: 'https://8083-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io',
  customer: 'https://8084-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io',
  platform: 'https://8087-baesj1-ktlibrary-w3b1w8wc93i.ws-us120.gitpod.io',
};

/**
 * HTTP 요청을 처리하는 기본 fetch 래퍼
 * @param {string} url - 요청 URL
 * @param {object} options - fetch 옵션
 * @returns {Promise} 응답 데이터
 */
const apiRequest = async (url, options = {}) => {
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers,
    },
    ...options,
  };

  try {
    console.log(`API 요청: ${options.method || 'GET'} ${url}`);
    
    const response = await fetch(url, defaultOptions);
    
    console.log(`API 응답: ${response.status} ${response.statusText}`);

    // 응답 처리
    if (!response.ok) {
      const errorText = await response.text();
      console.error('API 오류 응답:', errorText);
      
      throw new Error(
        response.status === 404 
          ? '요청한 리소스를 찾을 수 없습니다.'
          : response.status === 500
          ? '서버 내부 오류가 발생했습니다.'
          : response.status === 400
          ? errorText || '잘못된 요청입니다.'
          : `HTTP ${response.status}: ${response.statusText}`
      );
    }

    // JSON 응답 파싱
    const data = await response.json();
    console.log('API 응답 데이터:', data);
    
    return data;
  } catch (error) {
    console.error('API 요청 실패:', error);
    
    // 네트워크 오류 처리
    if (error.name === 'TypeError' && error.message.includes('fetch')) {
      throw new Error('서버에 연결할 수 없습니다. 네트워크 연결을 확인해주세요.');
    }
    
    throw error;
  }
};

/**
 * Author API 서비스
 */
export const authorAPI = {
  /**
   * 작가/관리자 로그인
   * @param {object} loginData - 로그인 데이터
   * @param {string} loginData.email - 이메일
   * @param {string} loginData.password - 비밀번호
   * @returns {Promise} 로그인 결과
   */
  login: async (loginData) => {
    const url = `${API_CONFIG.gateway}/authors/login`;
    
    const payload = {
      email: loginData.email,
      password: loginData.password
    };

    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  },

  /**
   * 작가 등록
   * @param {object} authorData - 작가 등록 데이터
   * @param {string} authorData.email - 이메일
   * @param {string} authorData.name - 이름
   * @param {string} authorData.introduction - 소개글
   * @param {string} authorData.password - 비밀번호
   * @returns {Promise} 등록된 작가 정보
   */
  registerAuthor: async (authorData) => {
    const url = `${API_CONFIG.gateway}/authors/registerauthor`;
    
    const payload = {
      email: authorData.email,
      name: authorData.name,
      introduction: authorData.introduction || '',
      password: authorData.password
    };

    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  },

  /**
   * 작가 목록 조회
   * @returns {Promise} 작가 목록
   */
  getAuthors: async () => {
    const url = `${API_CONFIG.gateway}/authors`;
    return await apiRequest(url);
  },

  /**
   * 특정 작가 조회
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 작가 정보
   */
  getAuthor: async (authorId) => {
    const url = `${API_CONFIG.gateway}/authors/${authorId}`;
    return await apiRequest(url);
  },

  /**
   * 이메일로 작가 검색
   * @param {string} email - 이메일
   * @returns {Promise} 작가 정보
   */
  getAuthorByEmail: async (email) => {
    const url = `${API_CONFIG.gateway}/authors/search/findByEmail?email=${encodeURIComponent(email)}`;
    return await apiRequest(url);
  },

  /**
   * 작가 승인
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 승인된 작가 정보
   */
  approveAuthor: async (authorId) => {
    const url = `${API_CONFIG.gateway}/authors/${authorId}/approveauthor`;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify({ isApproved: true }),
    });
  },

  /**
   * 작가 승인 거부
   * @param {number} authorId - 작가 ID
   * @returns {Promise} 거부된 작가 정보
   */
  disapproveAuthor: async (authorId) => {
    const url = `${API_CONFIG.gateway}/authors/${authorId}/disapproveauthor`;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify({ isApproved: false }),
    });
  },

  /**
   * 작가 정보 수정
   * @param {number} authorId - 작가 ID
   * @param {object} updateData - 수정할 데이터
   * @param {string} updateData.email - 새 이메일 (선택)
   * @param {string} updateData.introduction - 새 소개글 (선택)
   * @returns {Promise} 수정된 작가 정보
   */
  editAuthor: async (authorId, updateData) => {
    const url = `${API_CONFIG.gateway}/authors/${authorId}/editauthor`;
    
    const payload = {};
    if (updateData.email) payload.email = updateData.email;
    if (updateData.introduction !== undefined) payload.introduction = updateData.introduction;
    
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify(payload),
    });
  }
};

/**
 * Customer API 서비스
 */
export const customerAPI = {
  /**
   * 고객 로그인
   * @param {object} loginData - 로그인 데이터
   * @param {string} loginData.email - 이메일
   * @param {string} loginData.password - 비밀번호
   * @returns {Promise} 로그인 결과
   */
  login: async (loginData) => {
    const url = `${API_CONFIG.gateway}/customers/login`;
    
    const payload = {
      email: loginData.email,
      password: loginData.password
    };

    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  },

  /**
   * 고객 회원가입
   * @param {object} customerData - 고객 등록 데이터
   * @param {string} customerData.email - 이메일
   * @param {string} customerData.name - 이름
   * @param {string} customerData.password - 비밀번호
   * @param {boolean} customerData.isKtUser - KT 사용자 여부
   * @returns {Promise} 등록된 고객 정보
   */
  registerUser: async (customerData) => {
    const url = `${API_CONFIG.gateway}/customers/registeruser`;
    
    const payload = {
      email: customerData.email,
      name: customerData.name,
      password: customerData.password,
      isKtUser: customerData.isKtUser || false
    };

    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(payload),
    });
  },

  /**
   * 고객 목록 조회
   * @returns {Promise} 고객 목록
   */
  getCustomers: async () => {
    const url = `${API_CONFIG.gateway}/customers`;
    return await apiRequest(url);
  },

  /**
   * 특정 고객 조회
   * @param {number} customerId - 고객 ID
   * @returns {Promise} 고객 정보
   */
  getCustomer: async (customerId) => {
    const url = `${API_CONFIG.gateway}/customers/${customerId}`;
    return await apiRequest(url);
  },

  /**
   * 이메일로 고객 검색
   * @param {string} email - 이메일
   * @returns {Promise} 고객 정보
   */
  getCustomerByEmail: async (email) => {
    const url = `${API_CONFIG.gateway}/customers/search/findByEmail?email=${encodeURIComponent(email)}`;
    return await apiRequest(url);
  },

  /**
   * 도서 요청
   * @param {object} requestData - 도서 요청 데이터
   * @returns {Promise} 요청 결과
   */
  requestBook: async (requestData) => {
    const url = `${API_CONFIG.gateway}/customers/requestbook`;
    
    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(requestData),
    });
  }
};


/**
 * Manuscript API 서비스
 */
export const manuscriptAPI = {
  /**
   * 원고 등록
   */
  registerManuscript: async (data) => {
    const url = `${API_CONFIG.gateway}/manuscripts`;
    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  /**
   * 원고 수정
   */
  editManuscript: async (id, data) => {
    const url = `${API_CONFIG.gateway}/manuscripts/${id}`;
    return await apiRequest(url, {
      method: 'PUT',
      body: JSON.stringify(data),
    });
  },

  /**
   * 출판 요청 
   */
  requestPublishing: async (data) => {
    const url = `${API_CONFIG.gateway}/manuscripts/requestpublishing`; 
    return await apiRequest(url, {
      method: 'POST',
      body: JSON.stringify(data),
    });
  },

  /**
   * 전체 원고 목록 조회
   */
  getAllManuscripts: async () => {
    const url = `${API_CONFIG.gateway}/manuscripts`;
    return await apiRequest(url);
  },
};









/**
 * Platform API 서비스 (추후 확장용)
 */
export const platformAPI = {
  // TODO: Platform API 메소드들 구현
};

export default {
  authorAPI,
  customerAPI,
  platformAPI,
  manuscriptAPI,
};
