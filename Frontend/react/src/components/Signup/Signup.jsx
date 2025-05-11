import {useAuth} from "@/components/Context/AuthContext.jsx";
import {useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {Flex, Heading, Image, Link, Stack, Text} from "@chakra-ui/react";
import logo from "@/assets/ea706112-14df-4feb-a3a2-1a7b87af4929-removebg-preview.png";
import {motion} from "framer-motion";
import CreateCustomerForm from "@/components/shared/CreateCustomerForm.jsx";

const Signup = () => {

    const {customer, set} = useAuth();
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
                    <Heading fontSize={'2xl'} mb={15}>Register for an account</Heading>
                    <CreateCustomerForm onSuccess={(token) => {
                        localStorage.setItem("Access_Token", token);
                        setCustomerFromToken(token);
                        navigate("/Dashboard");
                    }}/>
                    <Link color={"blue.500"} href={"/"}>
                        Have an account? Login now.
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
};

export default Signup;