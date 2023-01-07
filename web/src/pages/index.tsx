import { Flex, Heading, Text, Textarea, Link } from '@chakra-ui/react';
import { useCallback, useState } from 'react';
import QRCode from 'react-qr-code';

export default function HomePage() {
  const [text, updateText] = useState('Escreva seu texto aqui');

  const handleUpdateText = useCallback((value: string) => {
    updateText(JSON.stringify(value));
  }, []);

  return (
    <Flex w="100%" h="90vh" align="center" justify="center">
      <Flex
        m={4}
        align="flex-start"
        gap={6}
        sx={{
          '@media (max-width: 700px)': {
            flexDir: 'column',
            alignItems: 'center',
          },
        }}
      >
        <Flex flexDir="column" gap={2}>
          <Heading as="h4" size="md">
            Texto
          </Heading>
          <Textarea
            resize="none"
            h="150px"
            defaultValue={''}
            placeholder="Escreva seu texto aqui."
            onChange={event => handleUpdateText(event.target.value)}
          />
          <Text textTransform="uppercase" color="gray.500" fontSize="0.7rem">
            Ao escanear QR Code mostrará este texto.
          </Text>
        </Flex>

        <Flex flexDir="column" w="200px">
          <Flex as={QRCode} w="200px" h="200px" value={text} />

          <Text fontSize="sm" textAlign="center" mt={2}>
            <Link
              href="/download/read-qr-code.apk"
              isExternal
              fontWeight="bold"
            >
              Clique aqui
            </Link>{' '}
            para fazer o <i>download do Aplicativo</i> de leitura para Android.
          </Text>
        </Flex>
      </Flex>
    </Flex>
  );
}
