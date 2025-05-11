import {
    Button,
    Checkbox,
    Flex,
    Text,
    FormControl,
    FormLabel,
    Heading,
    Input,
    Stack,
    Image, Link, Alert, AlertIcon, Box,
} from '@chakra-ui/react'
import { motion } from "framer-motion";
import {Formik, Form, useField} from "formik"; // eslint-disable-line no-unused-vars
import * as Yup from 'yup';
import logo from '../../assets/ea706112-14df-4feb-a3a2-1a7b87af4929-removebg-preview.png'
import {useAuth} from "@/components/Context/AuthContext.jsx";
import {errorNotification} from "@/services/notification.js";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";

const MyTextInput = ({label, ...props}) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <Box>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error" status={"error"} mt={2}>
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </Box>
    );
};

const LoginForm = () => {

    const { login } = useAuth();
    const navigate = useNavigate();

    return (
        <Formik
            validateOnMount={true}
            validationSchema={Yup.object({
                username: Yup.string().email('Must be valid email address').required('Required'),
                password: Yup.string()
                    .max(20, 'Password cannot be more than 20 characters')
                    .required('Required'),
            })}
            initialValues={{username: '', password: '',}}
            onSubmit={(values,{setSubmitting}) => {
                setSubmitting(true);
                login(values).then((res) => {
                    // TODO: navigate to dashboard
                    navigate("/Dashboard");
                    // console.log("Successfully loged in", res);
                }).catch((err) => {
                    errorNotification(
                        err.code,
                        err.response.data.message
                    )
                }).finally(()=>{
                    setSubmitting(false);
                })
        }}>

            {({isValid,isSubmitting}) => (
                <Form>
                    <Stack spacing={15}>
                        <MyTextInput
                            label={"Email Address"}
                            name={"username"}
                            type={"email"}
                            placeholder={"hello@gmail.com"}
                        />
                        <MyTextInput
                            label={"Password"}
                            name={"password"}
                            type={"password"}
                            placeholder={"Type your password"}
                        />

                        <Button disabled={!isValid || isSubmitting} type="submit">
                            Login
                        </Button>

                    </Stack>
                </Form>
            )}

        </Formik>
    )
}

const Login = () => {

    const {customer} = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(customer){
            navigate("/Dashboard");
        }
    });

    return (
        <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
            <Flex p={8} flex={1} align={'center'} justify={'center'}>
                <Stack spacing={4} w={'full'} maxW={'md'}>
                    <Image
                        src={logo}
                        boxSize={"100px"}
                        // width={"250px"}
                        alt={"Client Snap"}
                        alignSelf={"center"}
                        mb={10}
                    />
                    <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
                    <LoginForm/>
                    <Link color={"blue.500"} href={"/signup"}>
                        Dont have an account? Signup now.
                    </Link>
                </Stack>
            </Flex>
            <Flex
                flex={1}
                p={10}
                flexDirection={"column"}
                alignItems={"center"}
                justifyContent={"center"}
                bgGradient={{sm: 'linear(to-r, blue.600, purple.600)'}}
            >
                <motion.div
                    initial={{ opacity: 0, y: -50 }}
                    animate={{ opacity: 1, y: 0 }}
                    transition={{ duration: 1, type: "spring", stiffness: 100 }}
                >
                    <Text fontSize={"5xl"} color={'white'} fontWeight={"bold"} mb={5} isTruncated>
                        Organize. Manage. Grow.
                    </Text>
                </motion.div>

                <Image
                    alt={'Login Image'}
                    objectFit={'scale-down'}
                    src={
                        'https://user-images.githubusercontent.com/40702606/215539167-d7006790-b880-4929-83fb-c43fa74f429e.png'
                    }
                />
            </Flex>
        </Stack>
    )
}

export default Login;
