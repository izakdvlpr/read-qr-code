import { extendTheme } from '@chakra-ui/react';
import type { Styles } from '@chakra-ui/theme-tools';

export const theme = extendTheme({
  config: {
    initialColorMode: 'light',
    useSystemColorMode: false,
  },
  fonts: {
    heading: "'Poppins', sans-serif",
    body: "'Poppins', sans-serif",
  },
  styles: <Styles>{
    global: {
      '*:focus': {
        boxShadow: 'none !important',
      },
      'html': {
        scrollBehavior: 'smooth',
        overflowX: 'hidden',
      },
      'a': {
        _hover: {
          textDecoration: 'none',
        },
      },
    },
  },
});
