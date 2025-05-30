import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem('Access_Token')}`
    }
})

export const getCustomers = async () => {
    // eslint-disable-next-line no-useless-catch
    try{
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`
        , getAuthConfig())
    }
    catch (e){
        throw e;
    }
};

export const saveCustomer = async (customer) => {
    // eslint-disable-next-line no-useless-catch
    try {
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            customer);
    }catch (e){
        throw e;
    }
};

export const deleteCustomer = async (id) => {
    // eslint-disable-next-line no-useless-catch
    try{
        return await axios.delete(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`
        , getAuthConfig());
    }
    catch (e){
        throw e;
    }
};

export const login = async (usernameAndPassword) => {
    // eslint-disable-next-line no-useless-catch
    try{
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword
        );
    }
    catch (e){
        throw e;
    }
};