// Reference: https://www.programiz.com/c-programming/examples/swapping
#include<stdio.h>
int main() {
  double first, second, temp;
  printf("Enter the first number: ");
  scanf("%lf", &first);
  printf("Enter the second number: ");
  scanf("%lf", &second);

  temp = first;
  first = second;
  second = temp;

  printf("\nAfter swapping, first number = %.2lf\n", first);
  printf("After swapping, second number = %.2lf", second);
  return 0;
}